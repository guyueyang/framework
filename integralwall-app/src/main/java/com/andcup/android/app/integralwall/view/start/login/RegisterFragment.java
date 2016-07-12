package com.andcup.android.app.integralwall.view.start.login;


import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.andcup.android.app.integralwall.datalayer.IntegralWallDataLayer;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobGetUserInfo;
import com.andcup.android.app.integralwall.datalayer.job.JobUserRegister;
import com.andcup.android.app.integralwall.datalayer.job.JobVerifyCode;
import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.tools.LinkViewHelper;
import com.andcup.android.app.integralwall.view.navigator.NavigatorActivity;
import com.andcup.android.app.integralwall.view.start.login.base.AbsRegisterFragment;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.frame.datalayer.job.Schedule;
import com.andcup.android.integralwall.commons.tools.Phone;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.dd.processbutton.iml.ActionProcessButton;
import com.yl.android.cpa.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/14.
 */
public class RegisterFragment extends AbsRegisterFragment implements CompoundButton.OnCheckedChangeListener{

    @Bind(R.id.btn_verify_code)
    Button      mBtnGetVerify;
    @Bind(R.id.et_phone)
    EditText    mEtPhoneNumber;
    @Bind(R.id.et_verify_code)
    EditText mEtVerifyCode;
    @Bind(R.id.et_invite_code)
    EditText     mEtInviteCode;
    @Bind(R.id.et_password)
    EditText     mEtPassword;
    @Bind(R.id.cb_agree)
    CheckBox mCbAgree;
    @Bind(R.id.btn_register)
    ActionProcessButton mBtnRegister;
    @Bind(R.id.btn_password_eye)
    ImageButton mBtnPasswordEye;

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mCbAgree.setOnCheckedChangeListener(this);
        mBtnRegister.setMode(ActionProcessButton.Mode.ENDLESS);
        mBtnGetVerify.setText(getString(R.string.login_hint_verification));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @OnClick(R.id.btn_verify_code)
    public void onVerification(View view){
        String content = getPhoneNumber();
        if(TextUtils.isEmpty(content)){
            mEtPhoneNumber.setError(getResources().getString(R.string.login_error_phone));
            return;
        }
        if(!Phone.isPhoneNumber(content)){
            mEtPhoneNumber.setError(getResources().getString(R.string.login_error_phone_format));
            return;
        }
        startCountDown();
        call(new JobVerifyCode(content,JobVerifyCode.TYPE_REGISTER,0), new SimpleCallback<BaseEntity>() {
            @Override
            public void onError(Throwable e) {
                SnackBar.make(getActivity(), e.getMessage()).show();
                stopCountDown();
            }
        } );
    }

    private String getPhoneNumber(){
        return  mEtPhoneNumber.getText().toString();
    }

    @Override
    protected Button bindRetryVerifyCodeButton() {
        return mBtnGetVerify;
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

    @OnClick(R.id.btn_open_protocol)
    public void openProtocol(View view){

    }

    @OnClick(R.id.btn_register)
    protected void onConfirm(View view){
        String phoneNumber = mEtPhoneNumber.getText().toString();
        String verifyCode = mEtVerifyCode.getText().toString();
        String password   = mEtPassword.getText().toString();
        String inviteCode = mEtInviteCode.getText().toString();
        boolean ok = true;
        if(TextUtils.isEmpty(phoneNumber)){
            mEtPhoneNumber.setError(mEtPhoneNumber.getHint());
            ok=false;
        }
        if(password.length()<6){
            SnackBar.make(getActivity(), getString(R.string.error_Length_limit_small)).show();
            return;
        }
        if(password.length()>16){
            SnackBar.make(getActivity(), getString(R.string.error_length_limit_big)).show();
            return;
        }
        String regex = "^[a-z0-9A-Z_)(*&^%$#@!~`./.,<>?{}:;《》？，：;'。！!?.,]+$";
        if(!password.matches(regex)){
            SnackBar.make(getContext(),getString(R.string.limits_char_numbers)).show();
            return;
        }
        if(TextUtils.isEmpty(verifyCode)){
            mEtVerifyCode.setError(mEtVerifyCode.getHint());
            ok = false;
        }
        if(TextUtils.isEmpty(password)){
            mEtPassword.setError(mEtPassword.getHint());
            ok = false;
        }
        if(!ok){
            return;
        }
        mBtnRegister.setEnabled(false);
        mBtnRegister.setProgress(10);
        call(new JobUserRegister(phoneNumber, password, inviteCode, verifyCode), new SimpleCallback<BaseEntity<LoginEntity>>() {
            @Override
            public void onError(Throwable e) {
                mBtnRegister.setEnabled(true);
                mBtnRegister.setProgress(0);
                SnackBar.make(getActivity(), e.getMessage()).show();
            }

            @Override
            public void onSuccess(BaseEntity<LoginEntity> loginEntityBaseEntity) {
                SnackBar.make(getContext(),loginEntityBaseEntity.getMessage()).show();
                getUserInfo();
                go(NavigatorActivity.class);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mBtnRegister.setEnabled(isChecked);
        if(isChecked){
            mBtnRegister.setProgress(0);
        }else {
            mBtnRegister.setProgress(-1);
        }

    }

    @OnClick(R.id.btn_open_protocol)
    public void onOpenProtocol(){
        LinkViewHelper.open(getActivity(), IntegralWallDataLayer.getInstance().getPlatformConfigure().getPlatformUrl()+getString(R.string.protocol_address),getString(R.string.protocol));
    }

    private void getUserInfo(){
        call(new JobGetUserInfo(),new SimpleCallback<BaseEntity<UserInfoEntity>>());
    }
}
