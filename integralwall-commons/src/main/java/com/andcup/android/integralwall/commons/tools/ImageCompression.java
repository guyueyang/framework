package com.andcup.android.integralwall.commons.tools;

import android.content.Context;
import android.net.Uri;

import com.qioq.android.compressionImage.ImageCompress;
/**
 * Created by Amos on 2015/9/28.
 */
public class ImageCompression {
    public static String contentUri(Context context, String contentUri, boolean uri){
        try{
            ImageCompress imageCompress = new ImageCompress();
            ImageCompress.CompressOptions options = new ImageCompress.CompressOptions();
            if(uri){
                options.uri = Uri.parse(contentUri);
            }else{
                options.path = contentUri;
            }
            return imageCompress.compressFromUri(context, options);
        }catch (Exception e){
            return null;
        }
    }
}
