package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/25.
 */
public class JobShareSuccessful extends IntegralWallJob<BaseEntity> {
    @Override
    public BaseEntity start() throws IOException {
        String uid= UserProvider.getInstance().getUid();
        Long   time=getTimestamp();
        String sign=md5(uid+time+getKey());
        return apis().getShareSuccessful(uid,time,sign).execute().body();
    }
}
