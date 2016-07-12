package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.AbsAuthEntry;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/16.
 */
public class JobOtherLoginRegisterBind extends AbsJobUserLogin {

    AbsAuthEntry mAuthEntry;
    String mPhone;
    String mVerifyCode;
    String mpwd;

    public JobOtherLoginRegisterBind(AbsAuthEntry authEntry, String phone, String verifyCode,String pwd){
        mAuthEntry=authEntry;
        mPhone=phone;
        mVerifyCode=verifyCode;
        mpwd=pwd;
    }

    @Override
    public BaseEntity<LoginEntity> login() throws IOException {
        long time=getTimestamp();
        String type=String.valueOf(mAuthEntry.getType());
        String uid=mAuthEntry.getUid();
        String accessToken=mAuthEntry.getAccessToken();
        String location=mAuthEntry.getLocation();
        String sex=mAuthEntry.getSex();
        String nickName=mAuthEntry.getNickName();
        String avatar=mAuthEntry.getAvatar();
        String sign=md5(type + uid + mPhone + mVerifyCode + time + getKey());
        String password=md5(mpwd);
        return apis().otherlogin_registerbind(type, uid, location, nickName, avatar, sex, accessToken, time, password, mVerifyCode, mPhone,sign)
                .execute()
                .body();
    }
}
