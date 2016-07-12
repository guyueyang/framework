package com.andcup.android.app.integralwall.view.start.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.andcup.android.app.integralwall.datalayer.IntegralWallDataLayer;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobFindPassword;
import com.andcup.android.app.integralwall.datalayer.job.JobVerifyCode;
import com.andcup.android.app.integralwall.datalayer.model.base.AbsAuthEntry;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.tools.LinkViewHelper;
import com.andcup.android.app.integralwall.view.start.login.base.AbsRegisterFragment;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.frame.datalayer.job.JobEntity;
import com.andcup.android.integralwall.commons.tools.Phone;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.dd.processbutton.iml.ActionProcessButton;
import com.yl.android.cpa.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/11.
 */
public class FindPasswordFragment extends AbsRegisterFragment implements CompoundButton.OnCheckedChangeListener {

    @Bind(R.id.btn_verify_code)
    Button          mBtnVerifyCode;
    @Bind(R.id.et_phone)
    EditText mEtPhone;
    @Bind(R.id.et_verify_code)
    EditText        mEtVerifyCode;
    @Bind(R.id.et_password)
    EditText        mEtPassword;
    @Bind(R.id.et_confirm_password)
    EditText        mEtConfirmPassword;
    @Bind(R.id.btn_find_password)
    ActionProcessButton mBtnFindPassword;
    @Bind(R.id.cb_agree)
    CheckBox mCbAgree;
    @Bind(R.id.btn_password_eye)
    ImageButton mBtnPasswordEye;
    @Bind(R.id.btn_confirm_password_eye)
            ImageButton mBtnConfirmPasswordEye;
    AbsAuthEntry mAuthEntry;

    public static final int TYPE_FIND_PASSWORD = 1;

    @Override
    protected void afterActivityCreate(Bundle bundle) {
        super.afterActivityCreate(bundle);
//        mBtnVerifyCode.setText(getString(R.string.login_hint_verification));
//        mAuthEntry = UserCenter.INSTANCE.getAuthEntry();
        mCbAgree.setOnCheckedChangeListener(this);
        mBtnFindPassword.setMode(ActionProcessButton.Mode.ENDLESS);
        mBtnVerifyCode.setText(getString(R.string.login_hint_verification));
        setTitle(R.string.find_password);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find_password;
    }

    @Override
    protected Button bindRetryVerifyCodeButton() {
        return mBtnVerifyCode;
    }

    @OnClick(R.id.btn_find_password)
    public void findPassword(View view){
        String phoneNumber      = mEtPhone.getText().toString();
        String verifyCode       = mEtVerifyCode.getText().toString();
        String password         = mEtPassword.getText().toString();
        String confirmPassword  = mEtConfirmPassword.getText().toString();
        boolean ok = true;

        if(TextUtils.isEmpty(phoneNumber) ){
            mEtPhone.setError(mEtPhone.getHint());
            return;
        }

        if(TextUtils.isEmpty(verifyCode) ){
            mEtVerifyCode.setError(mEtVerifyCode.getHint());
            return;
        }

        if(TextUtils.isEmpty(password) ){
            mEtPassword.setError(mEtPassword.getHint());
            return;
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

        if(TextUtils.isEmpty(confirmPassword) ){
            mEtConfirmPassword.setError(getString(R.string.error_confirm_password));
            ok = false;
            return;
        }

        if(!password.equals(confirmPassword)){
            SnackBar.make(getActivity(), getString(R.string.error_not_same_password)).show();
            ok = false;
        }
        if(ok){
            mBtnFindPassword.setEnabled(false);
            mBtnFindPassword.setProgress(10);
            call(new JobFindPassword(phoneNumber, verifyCode, password), new SimpleCallback<JobEntity>() {
                @Override
                public void onError(Throwable e) {
                    mBtnFindPassword.setEnabled(true);
                    mBtnFindPassword.setProgress(0);
                    SnackBar.make(getActivity(), e.getMessage()).show();
                }

                @Override
                public void onSuccess(JobEntity jobEntity) {
                    SnackBar.make(getActivity(), getString(R.string.change_password_success)).show();
                    try {
                        getView().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getActivity().finish();
                            }
                        }, 1000);
                    } catch (Exception e) {

                    }
                }
            });
        }
    }

    @OnClick(R.id.btn_verify_code)
    public void onVerification(View view){
        String content = mEtPhone.getText().toString();
        if(TextUtils.isEmpty(content)){
            mEtPhone.setError(getResources().getString(R.string.login_error_phone));
            return;
        }
        if(!Phone.isPhoneNumber(content)){
            mEtPhone.setError(getResources().getString(R.string.login_error_phone_format));
            return;
        }
        startCountDown();
        call(new JobVerifyCode(content,JobVerifyCode.TYPE_FIND_PASSWORD,0), new SimpleCallback<BaseEntity>() {
            @Override
            public void onError(Throwable e) {
                SnackBar.make(getActivity(), e.getMessage()).show();
                stopCountDown();
            }
        } );
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
    @OnClick(R.id.btn_confirm_password_eye)
    public void eyeConfirmPassword(View view){
        int inputType = mEtConfirmPassword.getInputType();
        mEtConfirmPassword.setInputType(inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ?
                131201 : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        Editable text = mEtConfirmPassword.getText();
        mEtConfirmPassword.setSelection(text.length());
        if(inputType==InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
            mBtnConfirmPasswordEye.setImageResource(R.drawable.ic_eye_password_gone);
        }else {
            mBtnConfirmPasswordEye.setImageResource(R.drawable.ic_eye_password_visible);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mBtnFindPassword.setEnabled(isChecked);
        if(isChecked){
            mBtnFindPassword.setProgress(0);
        }else {
            mBtnFindPassword.setProgress(-1);
        }
    }

    @OnClick(R.id.btn_open_protocol)
    public void onOpenProtocol(){
        LinkViewHelper.open(getActivity(), IntegralWallDataLayer.getInstance().getPlatformConfigure().getPlatformUrl() + getString(R.string.protocol_address), getString(R.string.protocol));
    }
}
