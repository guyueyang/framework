package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/23.
 */
public class JobFirstLandingScore extends IntegralWallJob<BaseEntity> {
    @Override
    public BaseEntity start() throws IOException {
        long time=getTimestamp();
        String uid = UserProvider.getInstance().getUid();
        String sign=md5(uid+1+time+getKey());
        return apis().getFirstLandingScore(uid,1,time,sign).execute().body();
    }
}
