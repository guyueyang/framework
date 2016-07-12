package com.andcup.android.app.integralwall.datalayer.tools;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/9.
 */
public class DeviceUtils {

    public static int getSimState(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY ? 1: 0;
    }

    public static String getUniqueId() {
        String deviseId = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10;
        return MD5.toMd5(deviseId);
    }

    public static String getUniqueIdV2(Context context) {
        return MD5.toMd5(getUniqueId() + getIMEI(context) + getIMEI(context) + getIMSI(context));
    }

    public static String getUniqueId(Context context){
        return MD5.toMd5(getUniqueId() + getIMEI(context));
    }

    public static String getMacAddress(Context context) {
        String macAddress = null;
        WifiManager wifiMgr = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = null == wifiMgr?null:wifiMgr.getConnectionInfo();
        if(null != info) {
            macAddress = info.getMacAddress();
        }
        return macAddress;
    }

    public static String getIMEI(Context context) {
        return ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    public static String getIMSI(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getSubscriberId();
    }

    public static int getVerCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception var2) {
            Log.e("AndroidUtil", "Cannot find package and its version info.");
            return -1;
        }
    }

    public static String getVerName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception var2) {
            Log.e("AndroidUtil", "Cannot find package and its version info.");
            return "no version name";
        }
    }
}
