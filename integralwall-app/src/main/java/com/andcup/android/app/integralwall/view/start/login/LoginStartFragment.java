package com.andcup.android.app.integralwall.view.start.login;

import android.os.Bundle;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobGetUserInfo;
import com.andcup.android.app.integralwall.datalayer.job.JobVisitorUserLogin;
import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.view.navigator.NavigatorActivity;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.frame.datalayer.job.Schedule;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;

import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/24.
 */
@Deprecated
public class LoginStartFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_start_new;
    }

    @Override
    protected void afterActivityCreate(Bundle afterActivityCreate){
        super.afterActivityCreate(afterActivityCreate);
    }

    @OnClick(R.id.btn_visitor)
    protected void visitorLogin() {
        showLoading();
        call(new JobVisitorUserLogin(), new JobGetUserInfo(), Schedule.FLAT, new SimpleCallback<BaseEntity<LoginEntity>>() {
            @Override
            public void onSuccess(BaseEntity<LoginEntity> loginEntityBaseEntity) {
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
//        go2(LoginRegisterFragment.class,bundle);
        go2(NewLoginFragment.class,null);
    }

    @OnClick(R.id.btn_register)
    protected void Register(){
        showLoading();
        call(new JobVisitorUserLogin(), new JobGetUserInfo(), Schedule.FLAT, new SimpleCallback<BaseEntity<LoginEntity>>() {
            @Override
            public void onSuccess(BaseEntity<LoginEntity> loginEntityBaseEntity) {
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
}
