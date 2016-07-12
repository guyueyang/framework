package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.CalendarEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.frame.datalayer.exception.JobException;

import java.io.IOException;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/21.
 */
public class JobCalendar extends IntegralWallJob<BaseEntity<CalendarEntity>> {
    @Override
    public BaseEntity<CalendarEntity> start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long   time = getTimestamp();
        String sign = md5(uid + time + getKey());
        return apis().signIn(uid, time, sign).execute().body();
    }
}
