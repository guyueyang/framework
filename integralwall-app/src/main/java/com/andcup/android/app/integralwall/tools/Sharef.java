package com.andcup.android.app.integralwall.tools;

import android.app.Activity;
import android.content.SharedPreferences;

import com.andcup.android.app.integralwall.IntegralWallApplication;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.integralwall.commons.tools.NetUtils;

/**
 * Created by Amos on 2015/9/9.
 */
public class Sharef {

    private static final String PUSH = IntegralWallApplication.getInstance().getPackageName() + "pushState";
    private static final String PICT = IntegralWallApplication.getInstance().getPackageName() + "pictState";
    private static final String PHONE= IntegralWallApplication.getInstance().getPackageName() + "phone";
    private static final String QB_EXCHANGED = IntegralWallApplication.getInstance().getPackageName() + "qbExChanged";
    private static final String DOWNLOAD=IntegralWallApplication.getInstance().getPackageName() +"download";
    private static final String FIRST_RUN = IntegralWallApplication.getInstance().getPackageName() + "isFirstRun";
    private static final String INVITE_NOTIFY = IntegralWallApplication.getInstance().getPackageName() + "hasInviteNofity";

    public static void setInviteNotify(boolean firstRun){
        SharedPreferences firstRunPref = IntegralWallApplication.getInstance().getSharedPreferences(INVITE_NOTIFY + UserProvider.getInstance().getUid(),  Activity.MODE_PRIVATE);
        firstRunPref.edit().putBoolean(INVITE_NOTIFY + UserProvider.getInstance().getUid(), firstRun).commit();
    }

    public static boolean hasInviteNotify( ){
        SharedPreferences firstRunPref = IntegralWallApplication.getInstance().getSharedPreferences(INVITE_NOTIFY + UserProvider.getInstance().getUid(),  Activity.MODE_PRIVATE);
        return firstRunPref.getBoolean(INVITE_NOTIFY + UserProvider.getInstance().getUid(), false);
    }

    public static void setFirstRun(boolean firstRun){
        SharedPreferences firstRunPref = IntegralWallApplication.getInstance().getSharedPreferences(FIRST_RUN,  Activity.MODE_PRIVATE);
        firstRunPref.edit().putBoolean(FIRST_RUN, firstRun).commit();
    }

    public static boolean isFirstRun( ){
        SharedPreferences firstRunPref = IntegralWallApplication.getInstance().getSharedPreferences(FIRST_RUN,  Activity.MODE_PRIVATE);
        return firstRunPref.getBoolean(FIRST_RUN, true);
    }

    public static void setPushEnabled(boolean enabled){
        SharedPreferences pushShare= IntegralWallApplication.getInstance().getSharedPreferences(PUSH,  Activity.MODE_PRIVATE);
        pushShare.edit().putBoolean(PUSH, enabled).commit();
    }

    public static boolean isPushEnabled(){
        SharedPreferences pushShare= IntegralWallApplication.getInstance().getSharedPreferences(PUSH,  Activity.MODE_PRIVATE);
        return pushShare.getBoolean(PUSH, true);
    }

    public static void setFlowEnabled(boolean enabled){
        SharedPreferences picShare= IntegralWallApplication.getInstance().getSharedPreferences(PICT,  Activity.MODE_PRIVATE);
        picShare.edit().putBoolean(PICT, enabled).commit();
    }

    public static boolean isFlowEnabled(boolean judgeWifi){
        SharedPreferences picShare= IntegralWallApplication.getInstance().getSharedPreferences(PICT,  Activity.MODE_PRIVATE);
        boolean enabled = picShare.getBoolean(PICT, false);
        if(!judgeWifi){
            return enabled;
        }
        if(enabled){
            return NetUtils.isWifi(IntegralWallApplication.getInstance());
        }
        return true;
    }

    public static boolean hasQbExchanged(){
        SharedPreferences picShare= IntegralWallApplication.getInstance().getSharedPreferences(QB_EXCHANGED + UserProvider.getInstance().getUid(),  Activity.MODE_PRIVATE);
        return picShare.getBoolean(QB_EXCHANGED + UserProvider.getInstance().getUid(), false);
    }

    public static void setQbExchanged(boolean exchanged){
        SharedPreferences picShare= IntegralWallApplication.getInstance().getSharedPreferences(QB_EXCHANGED + UserProvider.getInstance().getUid(),  Activity.MODE_PRIVATE);
        picShare.edit().putBoolean(QB_EXCHANGED + UserProvider.getInstance().getUid(), exchanged).commit();
    }
    public static void setDownload(boolean enabled,String appPackage){
        SharedPreferences pushShare= IntegralWallApplication.getInstance().getSharedPreferences(appPackage,  Activity.MODE_PRIVATE);
        pushShare.edit().putBoolean(appPackage, enabled).commit();
    }

    public static boolean isDownload(String appPackage){
        SharedPreferences picShare= IntegralWallApplication.getInstance().getSharedPreferences(appPackage,  Activity.MODE_PRIVATE);
        return picShare.getBoolean(appPackage, false);
    }

    public static void setPhone(String phone){
        SharedPreferences picShare=IntegralWallApplication.getInstance().getSharedPreferences(PHONE,  Activity.MODE_PRIVATE);
        picShare.edit().putString(PHONE,phone).commit();
    }

    public static String getPhone(){
        SharedPreferences picShare= IntegralWallApplication.getInstance().getSharedPreferences(PHONE, Activity.MODE_PRIVATE);
        return picShare.getString(PHONE,"");
    }
}
