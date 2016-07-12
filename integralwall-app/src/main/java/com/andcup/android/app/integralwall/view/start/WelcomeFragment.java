package com.andcup.android.app.integralwall.view.start;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobGetUserInfo;
import com.andcup.android.app.integralwall.datalayer.job.JobVisitorUserLogin;
import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.tools.Sharef;
import com.andcup.android.app.integralwall.view.navigator.NavigatorActivity;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.start.login.LoginRegisterFragment;
import com.andcup.android.app.integralwall.view.start.login.NewLoginFragment;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.frame.datalayer.job.Schedule;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/18.
 */
public class WelcomeFragment extends BaseFragment {

    @Restore(BundleKey.DATA)
    String mPosition;
    @Bind(R.id.rl_background)
    View mBackground;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_welcome_screen_new;
    }

    @Override
    protected void afterActivityCreate(Bundle bundle) {
        super.afterActivityCreate(bundle);
        Sharef.setFirstRun(false);
    }

    @OnClick(R.id.btn_visitor)
    protected void visitorLogin() {
        showLoading();
        call(new JobVisitorUserLogin(), new JobGetUserInfo(), Schedule.FLAT, new SimpleCallback<BaseEntity<UserInfoEntity>>() {
            @Override
            public void onSuccess(BaseEntity<UserInfoEntity> userInfoEntity) {
                hideLoading();
                go(NavigatorActivity.class);
            }

            @Override
            public void onError(Throwable e) {
                hideLoading();
                SnackBar.make(getActivity(), e.getMessage()).show();
            }
        });
    }

    @OnClick(R.id.btn_login)
    protected void login(){
//        Bundle bundle = new Bundle();
//        bundle.putString(BundleKey.JUMP,"0");
        go2(NewLoginFragment.class,null);
    }

    @OnClick(R.id.btn_register)
    protected void Register(){
//        Bundle bundle = new Bundle();
//        bundle.putString(BundleKey.JUMP,"1");
//        go2(LoginRegisterFragment.class, bundle);
        showLoading();
        call(new JobVisitorUserLogin(), new JobGetUserInfo(), Schedule.FLAT, new SimpleCallback<BaseEntity<UserInfoEntity>>() {
            @Override
            public void onSuccess(BaseEntity<UserInfoEntity> userInfoEntity) {
                hideLoading();
                go(NavigatorActivity.class);
            }

            @Override
            public void onError(Throwable e) {
                hideLoading();
                String[] message=e.getMessage().split("&");
                try {
                    if(message[0].toString().equals("-111")){
                        go2(NewLoginFragment.class,null);
                        SnackBar.make(getActivity(), message[1].toString()).show();
                        return;
                    }
                }catch (Exception e1){
                }
                SnackBar.make(getActivity(), e.getMessage()).show();
            }
        });
    }
}
