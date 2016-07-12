package com.andcup.android.app.integralwall.third.sdk.base;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Amos on 2015/11/2.
 */
public abstract class ThirdSdk {

    @JsonProperty("appId")
    String mAppId;

    @JsonProperty("appKey")
    String mAppKey;

    public String getAppId() {
        return mAppId;
    }

    public String getAppKey() {
        return mAppKey;
    }

    public abstract void configSdk(Context context);

}
