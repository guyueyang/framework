package com.andcup.android.app.integralwall.third.sdk;

import android.content.Context;

import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.app.integralwall.third.sdk.base.ThirdSdk;
import com.yql.dr.sdk.DRSdk;

/**
 * Created by Administrator on 2016/1/25.
 */
public class DianRu extends ThirdSdk {

    @Override
    public void configSdk(Context context) {
        DRSdk.initialize(context, true, SdkManager.INST.mUid);
    }

    public void showOffers(Context context){
        DRSdk.showOfferWall(context, DRSdk.DR_OFFER);
    }

}
