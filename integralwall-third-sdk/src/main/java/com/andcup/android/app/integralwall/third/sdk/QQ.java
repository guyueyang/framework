package com.andcup.android.app.integralwall.third.sdk;

import android.app.Activity;
import android.content.Context;
import com.andcup.android.app.integralwall.third.sdk.base.ThirdSdk;
import com.umeng.socialize.sso.QZoneSsoHandler;

/**
 * Created by Amos on 2015/11/2.
 */
public class QQ extends ThirdSdk {

    @Override
    public void configSdk(Context context) {
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, getAppId(), getAppKey());
        qZoneSsoHandler.addToSocialSDK();
    }
}
