package com.andcup.android.app.integralwall.datalayer.job;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.andcup.android.app.integralwall.datalayer.IntegralWallDataLayer;
import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.intercepts.HeaderIntercept;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/18.
 */
public class JobPushApps extends IntegralWallJob<BaseEntity> {
    @Override
    public BaseEntity start() throws IOException {
        long time = getTimestamp();
        String sign = md5(HeaderIntercept.getUuid()+time+getKey());
        return apis().addApps(
                listApps(IntegralWallDataLayer.getInstance().getContext()),
                time,
                sign).execute().body();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String listApps(Context context){
        Map<String, String> appList = new HashMap<>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> mPacks = pm.getInstalledPackages(0);
        for(PackageInfo info : mPacks){
            if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                appList.put(info.packageName, isDownload(info.packageName)?"LYZ":"OTHER");
            }
        }
        return new JSONObject(appList).toString();
    }

    public static boolean isDownload(String appPackage){
        SharedPreferences picShare= IntegralWallDataLayer.getInstance().getContext().getSharedPreferences(appPackage,  Activity.MODE_PRIVATE);
        return picShare.getBoolean(appPackage, false);
    }
}
