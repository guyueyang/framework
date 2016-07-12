package com.andcup.sdk.push;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;


/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/23.
 */
public class PushHelper {

    static String TASK_ID = "taskId";
    static String MSG_ID = "msgId";
    static String PUSH_TYPE = "pushType";
    static String TITLE="title";

    public static Intent pushIntent(Bundle bundle){
        ComponentName cn = new ComponentName(
                "com.yl.android.cpa",
                "com.andcup.android.app.integralwall.view.IntegralWallPushActivity") ;
        Intent intent = new Intent() ;
        intent.putExtras(bundle);
        intent.setComponent(cn) ;
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static Intent familyIntent(Bundle bundle){
        ComponentName cn = new ComponentName(
                "com.yl.android.cpa",
                "com.andcup.android.app.integralwall.view.navigator.NavigatorActivity") ;
        Intent intent = new Intent() ;
        intent.setComponent(cn) ;
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static void openTask(Context content, String taskId){
        Bundle bundle = new Bundle();
        bundle.putString(TASK_ID, taskId);
        bundle.putSerializable(PUSH_TYPE, String.valueOf(PushSdk.MSG_TASK_DETAIL));
        content.startActivity(pushIntent(bundle));
    }

    public static void openMsg(Context content, String msgId){
        Bundle bundle = new Bundle();
        bundle.putSerializable(MSG_ID, msgId);
        bundle.putSerializable(PUSH_TYPE, String.valueOf(PushSdk.MSG_SYSTEM_MSG));
        content.startActivity(pushIntent(bundle));
    }

    public static void openCalendar(Context content){
        Bundle bundle = new Bundle();
        bundle.putSerializable(PUSH_TYPE, String.valueOf(PushSdk.MSG_SIGN));
        content.startActivity(pushIntent(bundle));
    }

    public static void openTaskList(Context content){
        Bundle bundle = new Bundle();
        bundle.putSerializable(PUSH_TYPE, String.valueOf(PushSdk.MSG_WAIT_FINISH_TASK));
        content.startActivity(pushIntent(bundle));
    }

    public static void openFamily(Context content){
        Bundle bundle = new Bundle();
        bundle.putSerializable(TITLE, "家族");
        bundle.putSerializable(PUSH_TYPE, String.valueOf(PushSdk.MSG_INVITE));
        content.startActivity(pushIntent(bundle));
        //EventBus.getDefault().post(new PushEvent(2));
    }
}
