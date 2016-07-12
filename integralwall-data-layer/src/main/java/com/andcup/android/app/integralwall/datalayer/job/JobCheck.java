package com.andcup.android.app.integralwall.datalayer.job;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.andcup.android.app.integralwall.datalayer.IntegralWallDataLayer;
import com.andcup.android.app.integralwall.datalayer.IntegralWallException;
import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.model.CheckEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseErrorEntity;
import com.andcup.android.app.integralwall.datalayer.tools.IOUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by Amos on 2015/11/27.
 */
public class JobCheck extends IntegralWallJob<BaseErrorEntity<CheckEntity>>{

    private static final int ERROR_DEVICE = -1024;
    private static final String TAG_CHECK = "tagCheck";

    int mSimState;
    public JobCheck(int simState){
        mSimState = simState;
    }

    private static boolean atBlackModel(List<String> modelList){
        for (int i = 0; null != modelList && i < modelList.size(); i++) {
            if (Build.MODEL.contains(modelList.get(i))) {
                return true;
            }
        }
        return false;
    }

    private static String checkBlacks(List<String> blackList){
        for( int i = 0; null != blackList && i< blackList.size(); i++){
            if(isPkgInstalled(IntegralWallDataLayer.getInstance().getContext(), blackList.get(i))){
                return blackList.get(i);
            }
        }
        return null;
    }

    private static boolean isPkgInstalled(Context ctx, String packageName) {
        PackageManager pm = ctx.getPackageManager();
        try {
            pm.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public BaseErrorEntity<CheckEntity> start() throws IOException {
        long time = getTimestamp();
        String serial = Build.SERIAL;
        String sign = md5(mSimState + serial + time + getKey());
        BaseErrorEntity<CheckEntity> entity = apis().checkAndroid(mSimState, serial, time, sign).execute().body();
        if(entity.getErrCode() > 0){
            String errorMessage ="";
            boolean blackModel = atBlackModel(entity.body().getModels());
            if(blackModel){
                //出现黑名单设备型号
                errorMessage += " blackModel = " + Build.MODEL;
                entity.setErrCode(ERROR_DEVICE);
            }
            String blacks = checkBlacks(entity.body().getBlacks());
            if(!TextUtils.isEmpty(blacks) ) {
                //出现黑名单应用
                errorMessage += "\n";
                errorMessage += " blacks = " + blacks;
                entity.setErrCode(ERROR_DEVICE);
            }
            if(!TextUtils.isEmpty(errorMessage)){
                entity.setMessage(errorMessage);
            }
        }
        log(JobCheck.class.getName(), entity.getMessage());
        if(entity.getErrCode() != ERROR_DEVICE && entity.getErrCode() < 0){
            throw  new IntegralWallException(entity.getMessage());
        }
        return entity;
    }
}
