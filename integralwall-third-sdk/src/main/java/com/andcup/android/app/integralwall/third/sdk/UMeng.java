package com.andcup.android.app.integralwall.third.sdk;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.app.integralwall.third.sdk.base.ThirdSdk;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.onlineconfig.OnlineConfigAgent;
import com.umeng.onlineconfig.UmengOnlineConfigureListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.update.UmengDownloadListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amos on 2015/11/2.
 */
public class UMeng extends ThirdSdk {

    private final String FOCE_UPDATE = "forceUpdate";
    private UmengUpdateListener mUmengUpdateListener;
    private Context mContext;

    @Override
    public void configSdk(Context context) {
        mContext = context;
        SocializeConstants.APPKEY = getAppKey();
        UmengUpdateAgent.setAppkey(getAppKey());
        UmengUpdateAgent.setChannel(SdkManager.INST.mChannel);

        MobclickAgent.UMAnalyticsConfig keyConfig =
                new MobclickAgent.UMAnalyticsConfig(context, getAppKey(), getAppId());
        MobclickAgent.startWithConfigure(keyConfig);
        MobclickAgent.setCatchUncaughtExceptions(true);

        OnlineConfigAgent.getInstance().setDebugMode(true);
    }

    public void setUpdateListener(UmengUpdateListener umengUpdateListener){
        mUmengUpdateListener = umengUpdateListener;
    }

    public void setOnlineConfigListener(UmengOnlineConfigureListener configListener){
        OnlineConfigAgent.getInstance().setOnlineConfigListener(configListener);
    }

    public void updateOnlineConfig(Context context){
        OnlineConfigAgent.getInstance().updateOnlineConfig(context, getAppKey(), getAppId());
    }

    public void checkUpdate(Context context){
        UmengUpdateAgent.setUpdateListener(mUmengUpdateListener);
        UmengUpdateAgent.setDeltaUpdate(true);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.update(context);
    }

    public boolean isForceUpdate(Context context){
        String forceUpdate = OnlineConfigAgent.getInstance().getConfigParams(context, FOCE_UPDATE);
        return forceUpdate.equals("true");
    }

    public boolean hasDownload(Context context, UpdateResponse updateResponse){
        File file = UmengUpdateAgent.downloadedFile(context, updateResponse);
        return null != file;
    }

    public void update(Context context, UpdateResponse updateResponse, UmengDownloadListener listener){
        File file = UmengUpdateAgent.downloadedFile(context, updateResponse);
        if( null == file){
            UmengUpdateAgent.setDownloadListener(listener);
            UmengUpdateAgent.startDownload(context, updateResponse);
        }
    }

    public void install(Context context, UpdateResponse updateResponse){
        File file = UmengUpdateAgent.downloadedFile(context, updateResponse);
        if( null != file){
            UmengUpdateAgent.startInstall(context, file);
        }
    }

    public static void doAuth(Activity activity, SHARE_MEDIA shareMedia, SocializeListeners.UMAuthListener listener) {
        UMSocialService umSocialService = UMServiceFactory.getUMSocialService("com.umeng.login");
        umSocialService.doOauthVerify(activity, shareMedia, listener);
    }

    public static void getPlatformInfo(Activity activity,  SHARE_MEDIA shareMedia,SocializeListeners.UMDataListener listener){
        UMSocialService umSocialService = UMServiceFactory.getUMSocialService("com.umeng.login");
        umSocialService.getPlatformInfo(activity, shareMedia, listener);
    }
    public static void onEvent(Context context, String id, Map<String, String> value){
        MobclickAgent.onEvent(context, id, value);
    }

    public static void onPageStart(String tag){
        MobclickAgent.onPageStart(tag);
    }

    public static void onPageEnd(String tag){
        MobclickAgent.onPageEnd(tag);
    }

    public static void onResume(Activity activity){
        MobclickAgent.onResume(activity);
    }

    public static void onPause(Activity activity) {
        MobclickAgent.onPause(activity);
    }
}
