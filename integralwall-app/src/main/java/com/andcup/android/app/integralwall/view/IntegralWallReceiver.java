package com.andcup.android.app.integralwall.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.andcup.android.app.integralwall.event.NetworkEvent;
import com.andcup.android.integralwall.commons.tools.NetUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/14.
 */
public class IntegralWallReceiver extends BroadcastReceiver {

    private static String NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(NET_CHANGE_ACTION)) {
            int state = NetUtils.isConnection(context)? NetworkEvent.TIME_CONNECTED : NetworkEvent.TIME_NOT_CONNECT;
            EventBus.getDefault().post(new NetworkEvent(state));
        }
    }

}
