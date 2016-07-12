package com.andcup.android.app.integralwall.datalayer.job;
import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/10.
 */
public class JobFindPassword extends IntegralWallJob<BaseEntity> {

    String mMobile;
    String mVerifyCode;
    String mPassword;

    public JobFindPassword(String mobile, String verifyCode, String password){
        mMobile=mobile;
        mVerifyCode=verifyCode;
        mPassword=password;
    }

    @Override
    public BaseEntity start() throws IOException {
        long time= getTimestamp();
        String pwMd5=md5(mPassword);
        String sign=md5(mMobile+mVerifyCode+pwMd5+time+getKey());
        return apis().findPassword(mMobile,mVerifyCode,pwMd5,time,sign)
                .execute()
                .body();
    }
}
