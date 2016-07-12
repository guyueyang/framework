package com.andcup.android.app.integralwall.event;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/16.
 */
public class AddSnapshotEvent {

    int mPosition;

    public AddSnapshotEvent(int position){
        mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }
}
