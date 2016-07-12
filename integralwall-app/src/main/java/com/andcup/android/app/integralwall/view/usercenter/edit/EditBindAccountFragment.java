package com.andcup.android.app.integralwall.view.usercenter.edit;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobUserRegister;
import com.andcup.android.app.integralwall.datalayer.job.JobVerifyCode;
import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.event.BindPhoneEvent;
import com.andcup.android.app.integralwall.view.start.login.base.AbsRegisterDialogFragment;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.integralwall.commons.tools.Phone;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/24.
 */
public class EditBindAccountFragment extends AbsRegisterDialogFragment {

    public static final int TYPE_REGISTER = 2;
    @Bind(R.id.btn_verify_code)
    Button      mBtnGetVerify;
    @Bind(R.id.et_phone)
    EditText mEtPhoneNumber;
    @Bind(R.id.et_verify_code)
    EditText mEtVerifyCode;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.et_confirm_password)
    EditText mEtConfirmPassword;
    @Bind(R.id.btn_confirm_password_eye)
    ImageButton mBtnConfirmPasswordEye;
    @Bind(R.id.btn_password_eye)
    ImageButton mBtnPasswordEye;

    @Override
    protected Button bindRetryVerifyCodeButton() {
        return mBtnGetVerify;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_bind_account;
    }

    @Override
    protected void afterActivityCreate(Bundle afterActivityCreate){
        super.afterActivityCreate(afterActivityCreate);
        mBtnGetVerify.setText(getString(R.string.login_hint_verification));
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
        call(new JobVerifyCode(content, TYPE_REGISTER, 0), new SimpleCallback<BaseEntity>() {
            @Override
            public void onError(Throwable e) {
                SnackBar.make(getActivity(), e.getMessage()).show();
                stopCountDown();
            }
        });
    }

    private String getPhoneNumber(){
        return  mEtPhoneNumber.getText().toString();
    }

    @Override
    protected void onOk(){
        final String phoneNumber = mEtPhoneNumber.getText().toString();
        if (!Phone.isPhoneNumber(phoneNumber)) {
            mEtPhoneNumber.setError(mEtPhoneNumber.getHint());
            //Toast.makeText(getActivity(), getString(R.string.login_error_phone_format), Toast.LENGTH_SHORT).show();
            return;
        }

        String verifyCode = mEtVerifyCode.getText().toString();
        if (TextUtils.isEmpty(verifyCode)) {
            mEtVerifyCode.setError(mEtVerifyCode.getHint());
            //Toast.makeText(getActivity(), getString(R.string.login_error_phone_format), Toast.LENGTH_SHORT).show();
            return;
        }

        String password = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mEtPassword.setError(mEtPassword.getHint());
            //Toast.makeText(getActivity(), getString(R.string.login_error_phone_format), Toast.LENGTH_SHORT).show();
            return;
        }

        String confirmPassword = mEtConfirmPassword.getText().toString();


        if(password.length()<6){
            SnackBar.make(getActivity(),getString(R.string.error_Length_limit_small)).show();
            return;
        }
        if(password.length()>16){
            SnackBar.make(getContext(),getString(R.string.error_length_limit_big)).show();
            return;
        }
        String regex = "^[a-z0-9A-Z_)(*&^%$#@!~`./.,<>?{}:;《》？，：;'。！!?.,]+$";
        if(!password.matches(regex)){
            SnackBar.make(getContext(),getString(R.string.limits_char_numbers)).show();
            return;
        }

        if(!password.equals(confirmPassword)){
            SnackBar.make(getContext(), getString(R.string.error_not_same_password)).show();

            return;
        }

        showLoading();

        String inviteCode = "";
        call(new JobUserRegister(phoneNumber,password,inviteCode,verifyCode), new SimpleCallback<BaseEntity<LoginEntity>>() {
            @Override
            public void onError(Throwable e) {
                SnackBar.make(getContext(),e.getMessage()).show();
                hideLoading();
            }

            @Override
            public void onSuccess(BaseEntity<LoginEntity> loginEntityBaseEntity) {
                SnackBar.make(getContext(),loginEntityBaseEntity.getMessage()).show();
                UserProvider.getInstance().getUserInfo().setMobile(phoneNumber);
                EventBus.getDefault().post(new BindPhoneEvent(0));
                dismissAllowingStateLoss();
            }
        });

    }
    @Override
    protected void onCancel(){
        dismissAllowingStateLoss();
    }

    @OnClick(R.id.btn_password_eye)
    public void eyePassword(View view){
        int inputType = mEtPassword.getInputType();
        mEtPassword.setInputType(inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD?
                131201: InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        Editable text = mEtPassword.getText();
        mEtPassword.setSelection(text.length());
        if(inputType==InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
            mBtnPasswordEye.setImageResource(R.drawable.ic_eye_password_gone);
        }else {
            mBtnPasswordEye.setImageResource(R.drawable.ic_eye_password_visible);
        }
    }
    @OnClick(R.id.btn_confirm_password_eye)
    public void eyeConfirmPassword(View view){
        int inputType = mEtConfirmPassword.getInputType();
        mEtConfirmPassword.setInputType(inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD?
                131201: InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        Editable text = mEtConfirmPassword.getText();
        mEtConfirmPassword.setSelection(text.length());
        if(inputType==InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
            mBtnConfirmPasswordEye.setImageResource(R.drawable.ic_eye_password_gone);
        }else {
            mBtnConfirmPasswordEye.setImageResource(R.drawable.ic_eye_password_visible);
        }
    }
}
