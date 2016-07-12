package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.CheckQbEntity;

import java.io.IOException;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/25.
 */
public class JobIsQbCanCheck extends IntegralWallJob<CheckQbEntity> {

    @Override
    public CheckQbEntity start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long time = getTimestamp();
        String sign = md5(uid + time + getKey());
        return apis().checkQbHasExChange(uid, time, sign).execute().body();
    }
}
