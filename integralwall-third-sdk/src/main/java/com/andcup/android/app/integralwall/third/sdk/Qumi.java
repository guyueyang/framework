package com.andcup.android.app.integralwall.third.sdk;

import android.content.Context;

import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.app.integralwall.third.sdk.base.ThirdSdk;
import com.newqm.pointwall.QEarnNotifier;
import com.newqm.pointwall.QSdkManager;


/**
 * Created by Amos on 2015/11/2.
 */

@Deprecated
public class Qumi extends ThirdSdk {

    @Override
    public void configSdk(Context context) {
        QSdkManager.init(context, getAppId(), getAppKey(), SdkManager.INST.mChannel, SdkManager.INST.mUid);
//        SdkManager.init(context, getAppId(), getAppKey(), SdkManager.INST.mChannel, SdkManager.INST.mUid);
    }

    public void showOffers(QEarnNotifier qEarnNotifier){
        QSdkManager.getsdkInstance().showOffers(qEarnNotifier);
    }
}
