package com.andcup.android.integralwall.commons.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/17.
 */
public class ScrolledViewPager extends ViewPager{

    private boolean mIsCanScroll;

    public ScrolledViewPager(Context context) {
        super(context);
    }

    public ScrolledViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
    // TODO Auto-generated method stub
        if (mIsCanScroll) {
            return super.onTouchEvent(arg0);
        } else {
            return false;
        }
    }

    public void setScrollable(boolean scrollable) {
        this.mIsCanScroll = scrollable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
    // TODO Auto-generated method stub
        if (mIsCanScroll) {
            return super.onInterceptTouchEvent(arg0);
        } else {
            return false;
        }
    }
}
