package com.andcup.android.app.integralwall.view.task.snapshot;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.yl.android.cpa.R;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/13.
 */
public class SnapshotImageView extends RoundedImageView {
    public SnapshotImageView(Context context) {
        super(context);
    }

    public SnapshotImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SnapshotImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addSnapshot() {
        Picasso.with(getContext())
                .load(R.drawable.ic_add_snapshot)
                .resize(125, 125)
                .placeholder(R.drawable.ic_add_snapshot)
                .into(this);
    }

    @Override
    public void setImageURI(Uri uri) {
        Glide.with(getContext())
                .load(uri)
                .centerCrop()
                .into(this);
    }
}
