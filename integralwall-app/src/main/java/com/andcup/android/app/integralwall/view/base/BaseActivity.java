package com.andcup.android.app.integralwall.view.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.andcup.android.app.integralwall.IntegralWallApplication;
import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.IntegralWallDataLayer;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.exception.AccountDisableException;
import com.andcup.android.app.integralwall.datalayer.exception.AnotherLoginException;
import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.app.integralwall.tools.Dialog;
import com.andcup.android.app.integralwall.view.dialog.AccountDisabledFragment;
import com.andcup.android.app.integralwall.view.IntegralWallActivity;
import com.andcup.android.app.integralwall.view.LoadingDialogFragment;
import com.andcup.android.app.integralwall.view.dialog.AnotherLoginFragment;
import com.andcup.android.app.integralwall.view.update.UpdateActivity;
import com.andcup.android.frame.view.RxAppCompatActivity;
import com.andcup.android.frame.view.navigator.ActivityNavigator;
import com.andcup.android.frame.view.navigator.DialogFragmentNavigator;
import com.andcup.android.frame.view.navigator.FragmentNavigator;
import com.andcup.android.update.Update;
import com.andcup.android.update.model.UpdateEntity;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UpdateResponse;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/9.
 */
public abstract class BaseActivity extends RxAppCompatActivity implements ColorBar{

    protected SystemBarTintManager mSystemBarTintManager;
    private boolean mAnotherLogin = false;

    protected void go(Class<? extends Activity> target){
        ActivityNavigator navigator = new ActivityNavigator(this);
        navigator.to(target).go().finish();
    }

    protected void go2Activity(Class<? extends Activity> target, Bundle bundle){
        ActivityNavigator navigator = new ActivityNavigator(this);
        navigator.to(target).with(bundle).go().finish();
    }

    protected void go(Class<? extends Fragment> target, int containerId){
        FragmentNavigator navigator = new FragmentNavigator(getSupportFragmentManager());
        navigator.at(containerId).to(target).go();
    }

    protected void finish(Class<? extends Fragment> target){
        FragmentNavigator navigator = new FragmentNavigator(getSupportFragmentManager());
        navigator.to(target).finish();
    }

    protected void go2(Class<? extends Fragment> target, Bundle value){
        IntegralWallActivity.go(this, target, value);
    }

    protected boolean isMonkey(){
        return IntegralWallDataLayer.getInstance().getPlatformConfigure().isMonkey();
    }

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
            mSystemBarTintManager.setTintColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void setBarColor(int color) {
        if( null != mSystemBarTintManager){
            mSystemBarTintManager.setTintColor(getResources().getColor(color));
        }
    }

    protected Point getWindowSize(){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return new Point(dm.widthPixels, dm.heightPixels);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SdkManager.INST.mUmeng.onResume(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SdkManager.INST.mUmeng.onPause(this);
        EventBus.getDefault().unregister(this);
    }

    protected void showLoading(){
        DialogFragmentNavigator navigator = new DialogFragmentNavigator(getSupportFragmentManager());
        navigator.to(LoadingDialogFragment.class).go();
    }

    protected void hideLoading(){
        DialogFragmentNavigator navigator = new DialogFragmentNavigator(getSupportFragmentManager());
        navigator.to(LoadingDialogFragment.class).finish();
    }

    @Subscribe
    public void onEventAnotherLogin(AnotherLoginException event){
        if(!mAnotherLogin){
            mAnotherLogin = true;
            Bundle bundle = new Bundle();
            bundle.putSerializable(BundleKey.CONTENT, getResources().getString(R.string.exit_offline));
            Dialog.ANOTHER_LOGIN.build(AnotherLoginFragment.class, bundle).show(getSupportFragmentManager());
        }
    }

    @Subscribe
    public void onEventAccountDisabled(AccountDisableException event){
        if(UserProvider.getInstance().getUserInfo() != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable(BundleKey.CONTENT, event.getValue());
            Dialog.ACCOUNT_DISABLED.build(AccountDisabledFragment.class, bundle).show(getSupportFragmentManager());
        }
    }

    @Subscribe
    public void onEventUpdate(UpdateResponse response){
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.UPDATE_RESPONSE, response);
        ActivityNavigator navigator = new ActivityNavigator(this);
        navigator.to(UpdateActivity.class).with(bundle).go();
    }

    @Subscribe
    public void update(UpdateEntity updateEntity){
        if(updateEntity.isUpgrade()){
            Update.getInstance().show(getSupportFragmentManager());
        }
    }
}
