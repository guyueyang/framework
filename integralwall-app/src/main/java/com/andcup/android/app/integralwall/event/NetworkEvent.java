package com.andcup.android.app.integralwall.event;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/14.
 */
public class NetworkEvent {

    public static final int TIME_OUT = 1;
    public static final int TIME_NOT_CONNECT = 2;
    public static final int TIME_CONNECTED = 3;

    int mState;
    String  mMessage;

    public NetworkEvent(int state){
        mState = state;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getMessage() {
        return mMessage;
    }

    public int getState() {
        return mState;
    }
}
