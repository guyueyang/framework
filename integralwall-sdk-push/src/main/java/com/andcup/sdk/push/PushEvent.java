package com.andcup.sdk.push;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/17.
 */
public class PushEvent {

    int mItem;

    public PushEvent(int item){
        mItem = item;
    }

    public int getItem() {
        return mItem;
    }
}
