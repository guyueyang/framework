package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/29.
 */
public class JobEditSex extends IntegralWallJob<BaseEntity> {

    int mSex;

    public JobEditSex(int sex){
        mSex=sex;
    }

    @Override
    public BaseEntity start() throws IOException {
        String uid= UserProvider.getInstance().getUid();
        Long time=getTimestamp();
        String sign=md5(uid+mSex+time+getKey());
        return apis().editSex(uid,mSex,time,sign).execute().body();
    }
}
