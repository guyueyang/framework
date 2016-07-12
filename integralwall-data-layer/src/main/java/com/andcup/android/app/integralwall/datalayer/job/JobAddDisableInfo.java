package com.andcup.android.app.integralwall.datalayer.job;

import android.os.Build;

import com.andcup.android.app.integralwall.datalayer.IntegralWallDataLayer;
import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseErrorEntity;
import com.andcup.android.app.integralwall.datalayer.tools.DeviceUtils;

import java.io.IOException;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/1.
 */
public class JobAddDisableInfo extends IntegralWallJob<BaseEntity>{

    int    mSimState;
    String mValue;

    public JobAddDisableInfo(int simState, String value){
        mSimState = simState;
        mValue    = value;
    }

    @Override
    public BaseEntity start() throws IOException {
        long time = getTimestamp();
        String sign = md5(time + getKey());
        String value = mValue + getOtherInfo(mSimState);
        BaseEntity  entity =  apis().addDisableDeviceInfo(value, time, sign).execute().body();
        log(JobAddDisableInfo.class.getName(), value + " \n result = " + entity.getMessage());
        return entity;
    }

    private static String getOtherInfo(int simState){
        String mac  = " MAX : " + DeviceUtils.getMacAddress(IntegralWallDataLayer.getInstance().getContext());
        String imei = " imei : " + DeviceUtils.getIMEI(IntegralWallDataLayer.getInstance().getContext());
        String imsi = " imsi : " + DeviceUtils.getIMSI(IntegralWallDataLayer.getInstance().getContext());
        String serial = " serial : " + Build.SERIAL;
        String sim = " sim : " + simState;
        String model = " model:" + Build.MODEL;
        return mac + imei + imsi + serial + sim + model;
    }
}
