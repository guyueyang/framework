package com.andcup.android.app.integralwall.view.start.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobGetUserInfo;
import com.andcup.android.app.integralwall.datalayer.job.JobOtherLoginRegisterBind;
import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.AbsAuthEntry;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.view.navigator.NavigatorActivity;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.frame.datalayer.job.Schedule;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.dd.processbutton.iml.ActionProcessButton;
import com.yl.android.cpa.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/18.
 */
public class SetPasswordFragment extends BaseFragment {

    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.btn_commit_changes)
    ActionProcessButton mBtnCommitChanges;
    @Restore(BundleKey.ABSAUTHENTRY)
    AbsAuthEntry mAuthEntry;
    @Restore(BundleKey.PHONE)
    String mPhone;
    @Restore(BundleKey.VERIFY_CODE)
    String mVerifyCode;
    @Bind(R.id.btn_password_eye)
    ImageButton mBtnPasswordEye;

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mBtnCommitChanges.setMode(ActionProcessButton.Mode.ENDLESS);
        setTitle(R.string.set_password);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_set_password;
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

    @OnClick(R.id.btn_commit_changes)
    protected void onConfirm(View view){
        String password   = mEtPassword.getText().toString();
        boolean ok = true;
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
        if(TextUtils.isEmpty(password)){
            mEtPassword.setError(mEtPassword.getHint());
            ok = false;
        }
        if(!ok){
            return;
        }
        mBtnCommitChanges.setEnabled(false);
        mBtnCommitChanges.setProgress(10);
        call(new JobOtherLoginRegisterBind(mAuthEntry,mPhone,mVerifyCode,password), new SimpleCallback<BaseEntity<LoginEntity>>() {
            @Override
            public void onError(Throwable e) {
                mBtnCommitChanges.setEnabled(true);
                mBtnCommitChanges.setProgress(0);
                SnackBar.make(getActivity(), e.getMessage()).show();
            }

            @Override
            public void onSuccess(BaseEntity<LoginEntity> loginEntityBaseEntity) {
                mBtnCommitChanges.setEnabled(true);
                mBtnCommitChanges.setProgress(0);
                getUserInfo();
                SnackBar.make(getActivity(), loginEntityBaseEntity.getMessage()).show();
                go(NavigatorActivity.class);
            }
        });
    }

    private void getUserInfo(){
        call(new JobGetUserInfo(),new SimpleCallback<BaseEntity<UserInfoEntity>>());
    }
}
