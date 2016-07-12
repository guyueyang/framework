package com.andcup.android.app.integralwall.tools;

import com.andcup.android.app.integralwall.event.OpenTaskDetailEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/17.
 */

public class TaskHelper {
    public static void  openTask(int taskId){
        EventBus.getDefault().post(new OpenTaskDetailEvent(taskId));
    }
}
