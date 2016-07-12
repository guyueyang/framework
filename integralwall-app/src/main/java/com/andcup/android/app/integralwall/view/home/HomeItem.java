package com.andcup.android.app.integralwall.view.home;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/15.
 */
public class HomeItem<T> {

    public static final int TYPE_TITLE          = 0x01;
    public static final int TYPE_NEW_USER_REWARD= 200;
    public static final int TYPE_DAILY_TASK     = 50;
    public static final int TYPE_TASK           = 0x4;
    public static final int TYPE_MORE           = 0x05;

    T       mData;
    int     mType;

    public HomeItem(int type){
        mType = type;
    }

    public void setData(T data) {
        this.mData = data;
    }

    public int getType() {
        return mType;
    }

    public T getData() {
        return mData;
    }
}
