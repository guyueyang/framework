package com.andcup.sdk.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.umeng.common.message.UmengMessageDeviceConfig;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MessageSharedPrefs;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.UmengRegistrar;
import com.umeng.message.entity.UMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/23.
 */
public class PushSdk {

    private static PushSdk sPushSdk;

//    消息推送类型（type）
//    1. 待完成任务消息
//    2. 系统通知消息推送
//    3. 活动推送.
//    4. 邀请推送.

    public static final int MSG_WAIT_FINISH_TASK = 1;
    public static final int MSG_SYSTEM_MSG = 2;
    public static final int MSG_TASK_DETAIL = 3;
    public static final int MSG_INVITE = 4;
    public static final int MSG_SIGN = 5;

    static final String MSG_TYPE    = "type";
    static final String MSG_CONTENT = "content";
    static final String MSG_ID      = "push_id";

    final String UID = "UID";

    static final List<String> sPushIdList = new ArrayList<>();

    PushAgent mPushAgent;
    Context   mContext;

    String    mAlias;

    public static final void init(Context context, String channel){
        if( null == sPushSdk){
            sPushSdk = new PushSdk(context, channel);
        }
    }

    private PushSdk(Context context, String channel){
        mContext = context;
        mPushAgent = PushAgent.getInstance(context);
        // init app key and secret.
        String key = "572bfaf167e58ee2f0002c81";
        mPushAgent.setAppkeyAndSecret(key ,"ca350dce9c284fc0cb6fc6510983db74");
        // set channel.
        mPushAgent.setMessageChannel(channel);
        // enable.
        mPushAgent.enable(mRegisterCallback);
        // on app start.
        mPushAgent.onAppStart();
        // 最多显示3条.
        mPushAgent.setDisplayNotificationNumber(3);

        // get device token.
        String deviceToken = UmengRegistrar.getRegistrationId(context);
        Log.e(PushSdk.class.getName(), "key = " + key);
        Log.e(PushSdk.class.getName(), "deviceToken = " + deviceToken);
        Log.e(PushSdk.class.getName(), "is enabled = " + PushAgent.getInstance(context).isEnabled());
        //check
        mPushAgent.setPushCheck(true);
        // debug 模式
        mPushAgent.setDebugMode(true);
        // 推送详细log.
        //com.umeng.common.message.Log.LOG = true;
        // umeng消息通知.
        mPushAgent.setNotificationClickHandler(mUmengNotification);

        updateStatus(context, mPushAgent);
    }

    public String getToken(){
        return mPushAgent.getRegistrationId();
    }

    private void removeAlias(String state, String tag, int type) {
        int e = 0;
        if(Build.VERSION.SDK_INT > 11) {
            e |= 4;
        }

        SharedPreferences c = mContext.getSharedPreferences("umeng_message_state", e);
        SharedPreferences.Editor var5 = c.edit();
        String var6 = "ALIAS" + state + type + "_" + tag;
        String var7 = "ALIAS" + state + type;
        var5.remove(var6);
        var5.remove(var7);
        Log.e("UTRACK", "var6 = " + var6 + "  var7 = " + var7);
        var5.commit();
    }

    private UmengNotificationClickHandler mUmengNotification = new UmengNotificationClickHandler(){
        @Override
        public void openActivity(Context context, UMessage uMessage) {
            int msgType = -1;
            String msgContent = null;
            for (Map.Entry<String, String> entry : uMessage.extra.entrySet()) {
                String key = entry.getKey();
                if(key.equals(MSG_TYPE)){
                    String value = entry.getValue();
                    msgType = Integer.valueOf(value);
                }else if(key.equals(MSG_CONTENT)){
                    msgContent =  entry.getValue();
                }else if(key.equals(MSG_ID)){
                    String msgId =  entry.getValue();
                    sPushIdList.add(msgId);
                }
            }
            dealWidthMsg(msgType, msgContent);
        }
    };

    public void dispose(){
        try {
            if(TextUtils.isEmpty(mAlias)){
                return;
            }
            //删除别名.
            mPushAgent.deleteAlias(mAlias, "UID");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String popPushId(){
        String id = null;
        if(sPushIdList.size() > 0){
            id = sPushIdList.get(0);
            sPushIdList.remove(0);
        }
        return id;
    }

    private void dealWidthMsg(int type, String content){
        switch (type){
            case MSG_WAIT_FINISH_TASK:
                PushHelper.openTaskList(mContext);
                break;
            case MSG_SYSTEM_MSG:
                PushHelper.openMsg(mContext, content);
                break;
            case MSG_TASK_DETAIL:
                PushHelper.openTask(mContext, content);
                break;
            case MSG_INVITE:
                PushHelper.openFamily(mContext);
                break;
            case MSG_SIGN:
                PushHelper.openCalendar(mContext);
                break;
        }
    }

    public static PushSdk getInstance(){
        return sPushSdk;
    }

    public void setAlias(String alias){
        try {
            if(TextUtils.isEmpty(alias) || TextUtils.isEmpty(mPushAgent.getRegistrationId())){
                return;
            }
            mAlias = alias;
            //取消别名
            dispose();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try{
                        //重新设置别名
                        removeAlias("SUCCESS_", UID, 3);
                        removeAlias("SUCCESS_", UID, 2);
                        removeAlias("FAIL_", UID, 2);
                        removeAlias("FAIL_", UID, 3);

                        mPushAgent.setExclusiveAlias(alias, "UID");
                    }catch (Exception e){

                    }
                }
            }, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStatus(Context context, PushAgent pushAgent) {
        String pkgName = context.getPackageName();
        String info = String.format("enabled:%s\nisRegistered:%s\nDeviceToken:%s\n" +
                        "SdkVersion:%s\nAppVersionCode:%s\nAppVersionName:%s",
                pushAgent.isEnabled(), pushAgent.isRegistered(),
                pushAgent.getRegistrationId(), MsgConstant.SDK_VERSION,
                UmengMessageDeviceConfig.getAppVersionCode(context), UmengMessageDeviceConfig.getAppVersionName(context));
        Log.e(PushSdk.class.getName(), "应用包名：" + pkgName + "\n" + info);
    }

    private IUmengRegisterCallback mRegisterCallback = new IUmengRegisterCallback() {

        @Override
        public void onRegistered(String registrationId) {
            // TODO Auto-generated method stub
            Log.e(PushSdk.class.getName(), "registrationId = " + registrationId);
            if(!TextUtils.isEmpty(mAlias)){
                setAlias(mAlias);
            }
        }
    };
}
