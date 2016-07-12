package com.andcup.android.app.integralwall.tools;

import com.andcup.android.app.integralwall.event.CheckNavigatorItemEvent;
import com.andcup.android.app.integralwall.event.ExitEvent;
import com.andcup.android.app.integralwall.event.LogoutEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/29.
 */
public class AppHelper {

    public static boolean monkey = true;

    public static void loginOut(){
        //退出对话框
        EventBus.getDefault().post(new LogoutEvent());
    }

    public static void exit(){
        EventBus.getDefault().post(new ExitEvent());
    }

    public static void checkNavigatorItemWithTask(){
        EventBus.getDefault().post(new CheckNavigatorItemEvent(1));
    }
}
