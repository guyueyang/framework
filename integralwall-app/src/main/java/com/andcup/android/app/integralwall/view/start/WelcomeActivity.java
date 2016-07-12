package com.andcup.android.app.integralwall.view.start;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.IntegralWallDataLayer;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobAddDisableInfo;
import com.andcup.android.app.integralwall.datalayer.job.JobCheck;
import com.andcup.android.app.integralwall.datalayer.job.JobPushApps;
import com.andcup.android.app.integralwall.datalayer.model.CheckEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseErrorEntity;
import com.andcup.android.app.integralwall.datalayer.tools.DeviceUtils;
import com.andcup.android.app.integralwall.event.LoginEvent;
import com.andcup.android.app.integralwall.event.ReCheckEvent;
import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.app.integralwall.tools.Dialog;
import com.andcup.android.app.integralwall.tools.Sharef;
import com.andcup.android.app.integralwall.view.base.BaseActivity;
import com.andcup.android.app.integralwall.view.base.BaseUpdateActivity;
import com.andcup.android.app.integralwall.view.dialog.BlackFragment;
import com.andcup.android.app.integralwall.view.navigator.NavigatorActivity;
import com.andcup.android.integralwall.commons.loading.LoadingIndicatorView;
import com.andcup.android.update.Update;
import com.andcup.android.update.model.UpdateEntity;
import com.andcup.widget.CirclePageIndicator;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;


/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/9.
 */
public class WelcomeActivity extends BaseUpdateActivity {

    @Bind(R.id.vp_welcome)
    ViewPager mVpWelcome;
    @Bind(R.id.cpi_welcome)
    CirclePageIndicator mCpiWelcome;
    @Bind(R.id.loading)
    LoadingIndicatorView mLoadingView;

    WelcomeAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        showLoading();
        call(new JobPushApps(), new SimpleCallback<>());
        mHandler.sendEmptyMessageDelayed(0, 2000);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            autoCheck();
        }
    };

    @Subscribe
    public void onLoginEvent(LoginEvent loginEvent){
        finish();
    }

    private void autoCheck(){

        if(IntegralWallDataLayer.getInstance().getPlatformConfigure().isCheck()){
            check();
        }else{
            hideLoading();
            start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(0);
    }

    @Override
    protected void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hideLoading() {
        mLoadingView.setVisibility(View.GONE);
    }

    private void start() {
        boolean isFirstRun = Sharef.isFirstRun();
        if (isFirstRun) {
            mAdapter = new WelcomeAdapter(this, getSupportFragmentManager(), 4);
            mVpWelcome.setAdapter(mAdapter);
            mCpiWelcome.setViewPager(mVpWelcome);
        } else {
            if (UserProvider.getInstance().getUserInfo() != null) {
                //自动登录上次账号.
                go(NavigatorActivity.class);
                return;
            }
            mVpWelcome.setVisibility(View.GONE);
            go(WelcomeFragment.class, R.id.fr_container);
        }
    }

    @Subscribe
    public void onCheck(ReCheckEvent checkEvent) {
        showLoading();
        mHandler.sendEmptyMessageDelayed(0, 2000);
    }

    private void check(){
        call(new JobCheck(DeviceUtils.getSimState(this)), new SimpleCallback<BaseErrorEntity<CheckEntity>>() {

            @Override
            public void onSuccess(BaseErrorEntity<CheckEntity> checkEntityBaseEntity) {
                if (checkEntityBaseEntity.getErrCode() > 0) {
                    hideLoading();
                    start();
                } else {
                    addCall(checkEntityBaseEntity.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                hideLoading();
                Bundle bundle = new Bundle();
                bundle.putSerializable(BundleKey.CONTENT, e.getMessage());
                Dialog.CHECK.build(BlackFragment.class, bundle).show(getSupportFragmentManager());
            }
        });
    }

    private void addCall(String value) {
        call(new JobAddDisableInfo(DeviceUtils.getSimState(this), value), new SimpleCallback<BaseErrorEntity>() {
            @Override
            public void onSuccess(BaseErrorEntity jobEntity) {
                hideLoading();
                Bundle bundle = new Bundle();
                if(DeviceUtils.getSimState(WelcomeActivity.this) == BlackFragment.SIM_NOT_READY){
                    bundle.putSerializable(BundleKey.CONTENT, getString(R.string.sim_non_ready));
                }else{
                    bundle.putSerializable(BundleKey.CONTENT, getString(R.string.real_env));
                }
                Dialog.CHECK.build(BlackFragment.class, bundle).show(getSupportFragmentManager());
            }

            @Override
            public void onError(Throwable e) {
                hideLoading();
                Bundle bundle = new Bundle();
                bundle.putSerializable(BundleKey.CONTENT, e.getMessage());
                Dialog.CHECK.build(BlackFragment.class, bundle).show(getSupportFragmentManager());
            }
        });
    }
}
