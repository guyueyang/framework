package com.andcup.android.app.integralwall.third.sdk;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.app.integralwall.third.sdk.base.ThirdSdk;
import com.andcup.android.update.Update;
import com.andcup.android.update.model.UpdateEntity;

import org.greenrobot.eventbus.EventBus;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/6/17.
 */
public class Updator extends ThirdSdk {
    @Override
    public void configSdk(Context context) {
        Update.getInstance().init(context);
        Update.getInstance().setAppId(getAppId());
        Update.getInstance().setAppKey(getAppKey());
        Update.getInstance().setChannel(SdkManager.INST.mChannel);
    }

    public boolean isShow(){
        return Update.getInstance().isShow();
    }

    public void show(FragmentManager fm){
        Update.getInstance().show(fm);
    }

    public void show( ){
        Update.getInstance().show();
    }

    public UpdateEntity getUpdateEntity(){
        return  Update.getInstance().getUpdateEntity();
    }

    public void checkUpdate(){
        Update.getInstance().checkUpdate(new Update.UpdateListener() {
            @Override
            public void onComplete(UpdateEntity response) {
                EventBus.getDefault().post(response);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void checkUpdate(Update.UpdateListener listener){
        Update.getInstance().checkUpdate(listener);
    }
}
