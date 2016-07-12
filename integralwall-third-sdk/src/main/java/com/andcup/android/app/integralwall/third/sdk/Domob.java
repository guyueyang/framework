package com.andcup.android.app.integralwall.third.sdk;

import android.app.Activity;
import android.content.Context;

import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.app.integralwall.third.sdk.base.ThirdSdk;

import cn.dow.android.DOW;
import cn.dow.android.listener.DLoadListener;


/**
 * Created by Amos on 2015/11/2.
 */
public class Domob extends ThirdSdk {

    @Override
    public void configSdk(Context context) {

    }

    public void initSdk(Context context,DLoadListener listener){
        DOW.getInstance(context).init(getAppKey(), listener);

    }

    public void showOffers(Context activity){
        DOW.getInstance(activity).show(activity);
        DOW.getInstance(activity).setUserId(SdkManager.INST.mUid);
    }
}
