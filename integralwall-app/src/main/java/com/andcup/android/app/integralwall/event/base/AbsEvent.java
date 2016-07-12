package com.andcup.android.app.integralwall.event.base;

import java.io.Serializable;

/**
 * Created by Amos on 2015/9/1.
 */
public abstract class AbsEvent implements Serializable {

    public static final int FIND_EVENT_DEFAULT = 0x0;
    public static final int FIND_EVENT_FAMILY  = 0x10;
    public static final int FIND_EVENT_POPULARIZE  = 0x11;


    public static final int TASK_WAIT   = 0;
    public static final int TASK_VERIFY = 1;
    public static final int TASK_FAILED = 2;
    public static final int TASK_FINISH = 3;

    protected int mEvent;

    public AbsEvent(int findEvent){
        mEvent = findEvent;
    }

    public int getEvent() {
        return mEvent;
    }
}
