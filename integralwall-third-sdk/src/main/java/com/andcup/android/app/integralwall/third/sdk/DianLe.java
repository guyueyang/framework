package com.andcup.android.app.integralwall.third.sdk;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.app.integralwall.third.sdk.base.ThirdSdk;
import com.longyicpa.DevInit;


/**
 * Created by Amos on 2015/11/2.
 */
public class DianLe extends ThirdSdk {

    @Override
    public void configSdk(Context context) {

    }

    public void initSdk(Activity activity) {
        DevInit.initGoogleContext(activity, getAppKey());

    }

    public void showOffers(Activity activity){
        DevInit.showOffers(activity);
        DevInit.setCurrentUserID(activity, SdkManager.INST.mUid);

    }

}
