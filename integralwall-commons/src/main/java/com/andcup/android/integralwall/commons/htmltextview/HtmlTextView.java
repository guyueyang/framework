package com.andcup.android.integralwall.commons.htmltextview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.internal.ListenerClass;

/**
 * Created by Amos on 2015/12/3.
 */
public class HtmlTextView extends TextView {

    public enum ScaleType{
        AUTO,
        FIT_WIDTH,
        NONE;
    }

    String     mText;
    ScaleType  mScaleType = ScaleType.AUTO;

    public HtmlTextView(Context context) {
        super(context);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScaleType(ScaleType scaleType){
        mScaleType = scaleType;
    }

    public void setHtmlText(final String text) {
        mText = text;
        if(mScaleType == ScaleType.NONE){
            setText(Html.fromHtml(text, mImageGetter, null));
        }else{
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    setText(Html.fromHtml(text, mImageGetter, null));
                }
            },10);
        }
    }

    Html.ImageGetter mImageGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String path) {
            URLDrawable drawable = new URLDrawable(path);
            Rect rect = drawable.getBounds();
            if(mScaleType == ScaleType.NONE){
                return drawable;
            }
            Glide.with(getContext()).load(path)
                    .asBitmap()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<Bitmap>(rect.width()/2, rect.height()/2) {

                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                            if (null != bitmap) {
                                drawable.setDrawable(new BitmapDrawable(bitmap));
                                HtmlTextView.this.postInvalidate();
                            }
                        }

                    });
            return drawable;
        }
    };

    public class URLDrawable extends BitmapDrawable {

        final String HEIGHT = "height:";
        final String WIDTH = "width:";
        final String PX = "px";

        Drawable mDrawable;
        String mUrl;
        Rect mRect;
        String mBoundsText;

        public URLDrawable(String url) {
            mUrl = url;
            mRect = genBounds();
            Log.e(URLDrawable.class.getName(), "url = " + mUrl + " width : " + mRect.width() + " height = " + mRect.height());
            this.setBounds(mRect);
        }

        private Rect genBounds() {
            // render style .
            int index = mText.indexOf(mUrl);
            mBoundsText = mText.substring(index + mUrl.length());
            return getDrawableBounds();
        }

        private Rect getDrawableBounds() {
            int width = getValue(WIDTH);
            int height = getValue(HEIGHT);
            switch (mScaleType){
                case AUTO:
                    return fitContent(width, height);
                case FIT_WIDTH:
                    return fitWidth(width, height);
                case NONE:
                    return fitRect(width, height);
            }
            return fitContent(width, height);
        }

        private int getValue(String tag) {
            int tagIndex = mBoundsText.indexOf(tag);
            String value = mBoundsText.substring(tagIndex + tag.length());
            int pxIndex = value.indexOf(PX);
            value = value.substring(0, pxIndex);
            return Integer.parseInt(value);
        }

        private Rect fitWidth(int width, int height) {
            int viewWidth = getViewWidth();
            float rate = ((float) viewWidth) / ((float) width);
            width  = viewWidth;
            height = (int) (height * rate);
            return new Rect(0, 0, width, height);
        }

        private Rect fitRect(int width, int height) {
            int viewWidth  = getViewWidth();
            int viewHeight = getViewHeight();
            if(viewWidth > viewHeight){
                width = height = viewHeight;
            }else{
                width = height = viewWidth;
            }
            return new Rect(0, 0, width, height);
        }

        private Rect fitContent(int width, int height) {
            int viewWidth = getViewWidth();
            float rate = ((float) viewWidth) / ((float) width);
            if (width > viewWidth) {
                width = viewWidth;
                height = (int) (height * rate);
            }
            return new Rect(0, 0, width, height);
        }

        private int getViewWidth() {
            return getWidth() - (getPaddingLeft() + getPaddingRight());
        }

        private int getViewHeight() {
            return getHeight() - (getPaddingTop() + getPaddingBottom());
        }

        public void setDrawable(Drawable drawable) {
            this.mDrawable = drawable;
            mDrawable.setBounds(0, 0, mRect.width(), mRect.height());
        }

        @Override
        public void draw(Canvas canvas) {
            if (mDrawable != null) {
                mDrawable.draw(canvas);
            }
        }
    }
}