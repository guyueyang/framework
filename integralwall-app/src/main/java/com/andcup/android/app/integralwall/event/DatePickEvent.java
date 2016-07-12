package com.andcup.android.app.integralwall.event;


import com.andcup.android.app.integralwall.event.base.AbsEvent;

/**
 * Created by Amos on 2015/9/16.
 */
public class DatePickEvent extends AbsEvent {

    String mDate;
    int    mType;

    public DatePickEvent(String date, int type) {
        super(FIND_EVENT_DEFAULT);
        mDate = date;
        mType = type;
    }

    public String getDate() {
        return mDate;
    }

    public int getType() {
        return mType;
    }
}
