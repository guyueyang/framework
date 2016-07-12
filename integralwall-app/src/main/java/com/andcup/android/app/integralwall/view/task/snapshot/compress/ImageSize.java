package com.andcup.android.app.integralwall.view.task.snapshot.compress;

/**
 * Created by 键盘 on 2016/6/5.
 *
 */
public class ImageSize {
    private final int width;
    private final int height;

    public ImageSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public ImageSize(int width, int height, int rotation) {
        if (rotation % 180 == 0) {
            this.width = width;
            this.height = height;
        } else {
            this.width = height;
            this.height = width;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
