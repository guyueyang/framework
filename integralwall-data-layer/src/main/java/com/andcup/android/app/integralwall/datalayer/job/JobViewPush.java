package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.IOException;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/31.
 */
public class JobViewPush extends IntegralWallJob<BaseEntity> {

    String mPushId;

    public JobViewPush(String pushId){
        mPushId = pushId;
    }

    @Override
    public BaseEntity start() throws IOException {
        String uid= UserProvider.getInstance().getUid();
        long   time=getTimestamp();
        String sign=md5(uid + mPushId + time + getKey());
        return apis().viewPush(uid, mPushId, time, sign).execute().body();
    }
}
