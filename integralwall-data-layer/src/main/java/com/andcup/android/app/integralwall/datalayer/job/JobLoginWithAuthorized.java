package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.AbsAuthEntry;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/16.
 */
public class JobLoginWithAuthorized extends AbsJobUserLogin {

    AbsAuthEntry mAuthEntry;

    public JobLoginWithAuthorized(AbsAuthEntry authEntry){
        mAuthEntry=authEntry;
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
        String sign=md5(type+uid+time+getKey());
        return apis().loginWithAuthorized(type,uid,location,nickName,avatar,sex,accessToken,time,sign)
                .execute()
                .body();
    }
}
