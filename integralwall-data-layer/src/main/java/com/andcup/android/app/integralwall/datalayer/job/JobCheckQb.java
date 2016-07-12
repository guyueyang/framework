package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.QbEntity;
import com.andcup.android.frame.datalayer.exception.JobException;

import java.io.IOException;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/25.
 */
public class JobCheckQb extends IntegralWallJob<QbEntity>{

    String mQQ;

    public JobCheckQb(String qq){
        mQQ = qq;
    }

    @Override
    public QbEntity start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long time = getTimestamp();
        String sign = md5(uid + mQQ + 11 + time + getKey());
        return apis().qbExchange(uid, mQQ, 11, time, sign).execute().body();
    }
}
