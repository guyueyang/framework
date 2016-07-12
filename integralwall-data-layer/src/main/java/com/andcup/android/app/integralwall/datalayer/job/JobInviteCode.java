package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.frame.datalayer.exception.JobException;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/26.
 */
public class JobInviteCode extends IntegralWallJob<BaseEntity> {

    String mInviteCode;

    public JobInviteCode(String inviteCode){
        mInviteCode=inviteCode;
    }

    @Override
    public BaseEntity start() throws IOException {
        String uid= UserProvider.getInstance().getUid();
        Long time=getTimestamp();
        String sign=md5(uid+mInviteCode+time+getKey());
        return apis().inviteCode(uid,mInviteCode,time,sign).execute().body();
    }
}
