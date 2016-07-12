package com.andcup.android.app.integralwall.third.sdk;

import android.content.Context;

import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.app.integralwall.third.sdk.base.ThirdSdk;
import com.bb.dd.BeiduoPlatform;



/**
 * Created by Amos on 2015/11/2.
 */
public class BeiDuo extends ThirdSdk {

    @Override
    public void configSdk(Context context) {
        BeiduoPlatform.setAppId(context, getAppId(), getAppKey(), 10007);

    }

    public void showOffers(Context context){
        BeiduoPlatform.showOfferWall(context);
        BeiduoPlatform.setUserId(SdkManager.INST.mUid);

    }

}
