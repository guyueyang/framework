package com.andcup.android.app.integralwall.event;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/17.
 */
public class CheckNavigatorItemEvent {

    int mItem;

    public CheckNavigatorItemEvent(int item){
        mItem = item;
    }

    public int getItem() {
        return mItem;
    }
}
