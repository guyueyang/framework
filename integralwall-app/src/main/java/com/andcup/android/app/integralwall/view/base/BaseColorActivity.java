package com.andcup.android.app.integralwall.view.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.andcup.android.app.integralwall.IntegralWallApplication;
import com.andcup.android.app.integralwall.IntegralWallUpdate;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobViewPush;
import com.andcup.sdk.push.PushSdk;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yl.android.cpa.R;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/9.
 */
public abstract class BaseColorActivity extends BaseUpdateActivity{

    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        setTranslucentStatus();
        IntegralWallApplication.INST.addActivity(this);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setTranslucentStatus() {
        if(!isMonkey()){
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            mSystemBarTintManager = new SystemBarTintManager(this);
            mSystemBarTintManager.setStatusBarTintEnabled(true);
            mSystemBarTintManager.setTintColor(getResources().getColor(R.color.colorActionBar));
        }
    }

    protected void callPushId(){
        String pushId = PushSdk.getInstance().popPushId();
        if(!TextUtils.isEmpty(pushId)){
            call(new JobViewPush(pushId), new SimpleCallback<>());
        }
    }
}
