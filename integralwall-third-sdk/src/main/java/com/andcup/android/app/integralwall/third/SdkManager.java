package com.andcup.android.app.integralwall.third;

import android.content.Context;
import android.util.Log;

import com.andcup.android.app.integralwall.third.sdk.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.dow.android.listener.DLoadListener;


/**
 * Created by Amos on 2015/11/2.
 */

public class SdkManager extends SdkHandler {

    public static SdkManager INST;

    @JsonProperty("qumi")
    public Qumi mQumi;

    @JsonProperty("QQ")
    public QQ mQQ;

    @JsonProperty("wechat")
    public WeChat mWeChat;

    @JsonProperty("weibo")
    public WeiBo mWeiBo;

    @JsonProperty("umeng")
    public UMeng mUmeng;

    @JsonProperty("beiduo")
    public BeiDuo mBeiDuo;

    @JsonProperty("dianle")
    public DianLe mDianLe;

    @JsonProperty("domob")
    public Domob mDomob;

    @JsonProperty("dianru")
    public DianRu mDianRu;

    @JsonProperty("update")
    public Updator mUpdate;

    public String mUid;
    public String mChannel;

    public static void initPlatform(Context context, String channel, boolean debug){
        if( null != SdkManager.INST){
            Log.e(SdkManager.class.getName(), "SdkManager has init!");
            return;
        }
        if(debug){
            SdkManager.INST = handler(context, "ThirdPlatform-debug.json", SdkManager.class);
        }else{
            SdkManager.INST = handler(context, "ThirdPlatform.json", SdkManager.class);
        }
        SdkManager.INST.mChannel = channel;
        SdkManager.INST.mUmeng.configSdk(context);
        SdkManager.INST.mWeChat.configSdk(context);
        SdkManager.INST.mWeiBo.configSdk(context);
        SdkManager.INST.mBeiDuo.configSdk(context);
        SdkManager.INST.mUpdate.configSdk(context);
        SdkManager.INST.mDomob.initSdk(context, new DLoadListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onLoading() {

            }
        });
    }

    public static void configOnActivityChanged(Context context){
        SdkManager.INST.mQQ.configSdk(context);
    }
}
