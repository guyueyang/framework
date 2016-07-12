package com.andcup.android.app.integralwall.view.start.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobGetUserInfo;
import com.andcup.android.app.integralwall.datalayer.job.JobUserLogin;
import com.andcup.android.app.integralwall.datalayer.job.JobVisitorUserLogin;
import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.AbsAuthEntry;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.event.LoginEvent;
import com.andcup.android.app.integralwall.tools.Sharef;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.navigator.NavigatorActivity;
import com.andcup.android.frame.datalayer.job.Schedule;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.dd.processbutton.iml.ActionProcessButton;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/28.
 */
public class PushLoginFragment extends BaseFragment {

    @Bind(R.id.et_content)
    EditText mEtPhone;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.btn_login)
    ActionProcessButton mBtnLogin;
    @Bind(R.id.btn_password_eye)
    ImageButton mBtnPasswordEye;
    AbsAuthEntry mAuthEntry;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_new;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTvTitle.setText(R.string.login);
        mBtnLogin.setMode(ActionProcessButton.Mode.ENDLESS);
        mEtPhone.setText(Sharef.getPhone());
    }

    @OnClick(R.id.btn_password_eye)
    public void eyePassword(View view){
        int inputType = mEtPassword.getInputType();
        mEtPassword.setInputType(inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ?
                131201 : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        Editable text = mEtPassword.getText();
        mEtPassword.setSelection(text.length());
        if(inputType==InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
            mBtnPasswordEye.setImageResource(R.drawable.ic_eye_password_gone);
        }else {
            mBtnPasswordEye.setImageResource(R.drawable.ic_eye_password_visible);
        }
    }

    @OnClick(R.id.btn_find_password)
    public void findPassword(View view){
        go2(FindPasswordFragment.class, null);
    }

    @OnClick(R.id.btn_login)
    public void doLogin(View view){
        String username = mEtPhone.getText().toString();
        String password = mEtPassword.getText().toString();
        boolean ok = true;
        if(TextUtils.isEmpty(username) ){
            mEtPhone.setError(mEtPhone.getHint());
            ok = false;
        }
        if (TextUtils.isEmpty(password)) {
            mEtPassword.setError(mEtPassword.getHint());
            ok = false;
        }
        if (!ok) {
            return;
        }
        mBtnLogin.setProgress(10);
        mBtnLogin.setEnabled(false);
        call(new JobUserLogin(username, password), new JobGetUserInfo(), Schedule.FLAT, new SimpleCallback<BaseEntity<LoginEntity>>() {
            @Override
            public void onSuccess(BaseEntity<LoginEntity> loginEntityBaseEntity) {
                go3(NavigatorActivity.class, getArguments());
                EventBus.getDefault().post(new LoginEvent());
                Sharef.setPhone(username);
            }

            @Override
            public void onError(Throwable e) {
                mBtnLogin.setEnabled(true);
                mBtnLogin.setProgress(0);
                String[] message=e.getMessage().split("&");
                try {
                    if(message[0].toString().equals("-111")){
                        SnackBar.make(getActivity(), message[1].toString()).show();
                        return;
                    }
                }catch (Exception e1){
                }
                SnackBar.make(getActivity(), e.getMessage()).show();
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onBack(){
        getActivity().finish();
    }

    @OnClick(R.id.btn_visitor)
    protected void visitorLogin() {
        showLoading();
        call(new JobVisitorUserLogin(), new JobGetUserInfo(), Schedule.FLAT, new SimpleCallback<BaseEntity<UserInfoEntity>>() {
            @Override
            public void onSuccess(BaseEntity<UserInfoEntity> userInfoEntity) {
                hideLoading();
                go3(NavigatorActivity.class, getArguments());
                EventBus.getDefault().post(new LoginEvent());
            }
            @Override
            public void onError(Throwable e) {
                hideLoading();
                String[] message=e.getMessage().split("&");
                try {
                    if(message[0].toString().equals("-111")){
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
