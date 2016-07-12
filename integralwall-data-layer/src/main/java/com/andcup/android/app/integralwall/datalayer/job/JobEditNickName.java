package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/24.
 */
public class JobEditNickName extends IntegralWallJob<BaseEntity> {
    String mNickName;

    public JobEditNickName(String nickName){
        mNickName=nickName;
    }
    @Override
    public BaseEntity start() throws IOException {
        String uid= UserProvider.getInstance().getUid();
        Long time=getTimestamp();
        String sign=md5(uid+time+getKey());
        return apis().editNickName(uid,mNickName,time,sign).execute().body();
    }
}
