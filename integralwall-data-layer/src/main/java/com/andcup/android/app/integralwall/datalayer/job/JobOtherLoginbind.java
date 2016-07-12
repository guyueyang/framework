package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.AbsAuthEntry;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/16.
 */
public class JobOtherLoginbind extends AbsJobUserLogin {

    AbsAuthEntry mAuthEntry;
    String mPhone;
    String mVerifyCode;

    public JobOtherLoginbind(AbsAuthEntry authEntry,String phone,String verifyCode){
        mAuthEntry=authEntry;
        mPhone=phone;
        mVerifyCode=verifyCode;
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
        String sign=md5(type+uid+mPhone+mVerifyCode+time+getKey());
        return apis().otherlogin_bind(type,uid,accessToken,mPhone,mVerifyCode,time,sign)
                .execute()
                .body();
    }
}
