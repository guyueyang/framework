package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/10.
 */
public class JobUserLogin extends AbsJobUserLogin {

    String mUsername;
    String mPassword;

    public JobUserLogin(String username, String password){
        mUsername=username;
        mPassword=password;
    }

    @Override
    public BaseEntity<LoginEntity> login() throws IOException {
        long time = getTimestamp();
        String pwMd5=md5(mPassword);
        String sign=md5(mUsername+pwMd5+time+getKey());
        return apis().login(mUsername,pwMd5,time,sign)
                .execute()
                .body();
    }
}
