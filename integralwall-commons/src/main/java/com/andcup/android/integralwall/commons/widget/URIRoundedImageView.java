package com.andcup.android.integralwall.commons.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import com.andcup.android.integralwall.commons.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/18.
 */
public class URIRoundedImageView extends RoundedImageView {

    public URIRoundedImageView(Context context) {
        super(context);
    }

    public URIRoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public URIRoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setImageURI(Uri uri) {
        Picasso.with(getContext())
                .load(uri)
                .resize(100, 100)
                .placeholder(R.drawable.ic_empty)
                .error(R.drawable.ic_empty)
                .into(this);
    }

    public void setEditImageURI(Uri uri) {
        Picasso.with(getContext()).load(uri).into(this);
    }

    public void initWithAvatar() {
        Picasso.with(getContext()).load(R.drawable.ic_empty).into(this);
    }
}
