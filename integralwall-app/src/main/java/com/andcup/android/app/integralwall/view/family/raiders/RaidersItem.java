package com.andcup.android.app.integralwall.view.family.raiders;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/24.
 */
public class RaidersItem<T> {
    public static final int TYPE_TITLE          = 0x01;
    public static final int TYPE_CONTENT        = 0x02;

    T       mData;
    int     mType;

    public RaidersItem(int type){
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
