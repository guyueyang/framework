package com.andcup.android.app.integralwall.view.base;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andcup.android.app.integralwall.event.NetworkEvent;
import com.andcup.android.frame.datalayer.exception.NetworkException;
import com.andcup.android.frame.datalayer.exception.TimeOutException;
import com.andcup.android.integralwall.commons.tools.NetUtils;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.BindString;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/14.
 */
public abstract class BaseNetworkActivity extends BaseColorActivity {

    @Bind(R.id.tv_network)
    TextView mTvTitle;

    @BindString(R.string.network_not_connection)
    String mNotConnection;

    @BindString(R.string.network_time_out)
    String mConnectionTimeOut;

    @Override
    protected void onResume() {
        super.onResume();
        int state = NetUtils.isConnection(this)? NetworkEvent.TIME_CONNECTED : NetworkEvent.TIME_NOT_CONNECT;
        onEventNetworkChanged(new NetworkEvent(state));
    }

    @Subscribe
    public void onEventNetworkChanged(NetworkEvent changedEvent){
        mTvTitle.setVisibility(View.VISIBLE);
        switch (changedEvent.getState()){
            case NetworkEvent.TIME_CONNECTED:
                mTvTitle.setVisibility(View.GONE);
                break;
            case NetworkEvent.TIME_NOT_CONNECT:
                mTvTitle.setText(mNotConnection);
                break;
            case NetworkEvent.TIME_OUT:
                mTvTitle.setText(mConnectionTimeOut);
                break;
        }
    }
}
