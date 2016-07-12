package com.andcup.android.app.integralwall.view.task.snapshot.compress;

import android.graphics.Bitmap;
import android.opengl.GLES10;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by 键盘 on 2016/6/5.
 *
 */
public class ImageUtils {
    private static final int DEFAULT_MAX_BITMAP_DIMENSION = 2048;

    private static ImageSize maxBitmapSize;

    static {
        int[] maxTextureSize = new int[1];
        GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxTextureSize, 0);
        int maxBitmapDimension = Math.max(maxTextureSize[0], DEFAULT_MAX_BITMAP_DIMENSION);
        maxBitmapSize = new ImageSize(maxBitmapDimension, maxBitmapDimension);
    }

    private ImageUtils() {
    }

    public static int computeImageSampleSize(ImageSize srcSize, ImageSize targetSize){
        final int srcWidth = srcSize.getWidth();
        final int srcHeight = srcSize.getHeight();
        final int targetWidth = targetSize.getWidth();
        final int targetHeight = targetSize.getHeight();

        int scale = 1;
        scale = Math.min(srcWidth / targetWidth, srcHeight / targetHeight); // min
        if (scale < 1) {
            scale = 1;
        }
        scale = considerMaxTextureSize(srcWidth, srcHeight, scale, false);

        return scale;
    }

    /**
     * 如果宽度和高度/scale大于max texture size则继续缩小
     * @return sample size
     */
    private static int considerMaxTextureSize(int srcWidth, int srcHeight, int scale, boolean powerOf2) {
        final int maxWidth = maxBitmapSize.getWidth();
        final int maxHeight = maxBitmapSize.getHeight();
        while ((srcWidth / scale) > maxWidth || (srcHeight / scale) > maxHeight) {
            if (powerOf2) {
                scale *= 2;
            } else {
                scale++;
            }
        }
        return scale;
    }

    public static float computeImageScale(ImageSize srcSize, ImageSize targetSize,
                                          boolean stretch) {
        final int srcWidth = srcSize.getWidth();
        final int srcHeight = srcSize.getHeight();
        final int targetWidth = targetSize.getWidth();
        final int targetHeight = targetSize.getHeight();

        final float widthScale = (float) srcWidth / targetWidth;
        final float heightScale = (float) srcHeight / targetHeight;

        final int destWidth;
        final int destHeight;
        if (widthScale < heightScale) {
            destWidth = targetWidth;
            destHeight = (int) (srcHeight / widthScale);
        } else {
            destWidth = (int) (srcWidth / heightScale);
            destHeight = targetHeight;
        }

        float scale = 1;
        if ((!stretch && destWidth < srcWidth && destHeight < srcHeight) || (stretch && destWidth != srcWidth && destHeight != srcHeight)) {
            scale = (float) destWidth / srcWidth;
        }

        return scale;
    }

    public static void compressBmpToFile(Bitmap bmp, File file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 300) {
            options -= 10;
            if (options > 0) {
                baos.reset();
                bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
            } else {
                break;
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = baos.toByteArray();
            fos.write(bytes);
            fos.flush();
            fos.close();
            Log.i("tag", "file.length" + file.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
