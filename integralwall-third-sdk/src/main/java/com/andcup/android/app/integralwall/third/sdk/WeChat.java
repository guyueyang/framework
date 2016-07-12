package com.andcup.android.app.integralwall.third.sdk;

import android.content.Context;

import com.andcup.android.app.integralwall.third.sdk.base.ThirdSdk;
import com.umeng.socialize.weixin.controller.UMWXHandler;

/**
 * Created by Amos on 2015/11/2.
 */
public class WeChat extends ThirdSdk {

    @Override
    public void configSdk(Context context) {

        UMWXHandler wxHandler = new UMWXHandler(context, getAppId(), getAppKey());
        wxHandler.addToSocialSDK();

        UMWXHandler wxCircleHandler = new UMWXHandler(context,getAppId(),getAppKey());
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }
}
