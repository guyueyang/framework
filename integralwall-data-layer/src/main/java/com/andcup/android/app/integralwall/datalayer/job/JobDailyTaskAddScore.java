package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/23.
 */
public class JobDailyTaskAddScore extends IntegralWallJob<BaseEntity> {
    @Override
    public BaseEntity start() throws IOException {
        long time=getTimestamp();
        String uid = UserProvider.getInstance().getUid();
        String sign=md5(uid+time+getKey());
        return apis().getDailyTaskAddScore(uid,time,sign).execute().body();
    }
}
