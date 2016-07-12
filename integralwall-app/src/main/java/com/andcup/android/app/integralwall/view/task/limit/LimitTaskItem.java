package com.andcup.android.app.integralwall.view.task.limit;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/20.
 */
public class LimitTaskItem<T> {

    public static final int TITLE   = 1;
    public static final int CONTENT = 2;

    T   mData;
    int mType;

    public LimitTaskItem(T data, int type){
        mData = data;
        mType = type;
    }

    public T body(){
        return mData;
    }

    public int getType(){
        return mType;
    }
}
