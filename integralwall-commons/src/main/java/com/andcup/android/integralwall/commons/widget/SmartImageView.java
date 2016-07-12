package com.andcup.android.integralwall.commons.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/17.
 */
public class SmartImageView extends ImageView {
    public SmartImageView(Context context) {
        super(context);
    }

    public SmartImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageURI(Uri uri) {
        Glide.with(getContext())
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this);
    }
}
