package com.andcup.android.app.integralwall.datalayer.model.base;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Amos on 2015/9/22.
 */
public abstract class AbsAuthEntry implements Serializable {

    public static final int AUTH_TYPE_WEIBO = 1;
    public static final int AUTH_TYPE_WEINXIN = 2;
    public static final int AUTH_TYPE_QQ = 3;
    public static final int AUTH_TYPE_TAOBAO=4;


    @JsonProperty("access_token")
    String mAccessToken;

    public abstract String getUid();

    public abstract String getNickName();

    public abstract String getAvatar();

    public abstract String getSex();

    public abstract String getLocation();

    public abstract int    getType();

    public String getAccessToken(){
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        this.mAccessToken = accessToken;
    }
}
