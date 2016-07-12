package com.andcup.android.app.integralwall.view.start.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobGetUserInfo;
import com.andcup.android.app.integralwall.datalayer.job.JobOtherLoginbind;
import com.andcup.android.app.integralwall.datalayer.job.JobVerifyCode;
import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.AbsAuthEntry;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.view.navigator.NavigatorActivity;
import com.andcup.android.app.integralwall.view.start.login.base.AbsRegisterFragment;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.tools.Phone;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.dd.processbutton.iml.ActionProcessButton;
import com.yl.android.cpa.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/11.
 */
public class BindAccountFragment extends AbsRegisterFragment {

    @Bind(R.id.btn_verify_code)
    Button      mBtnGetVerify;
    @Bind(R.id.et_phone)
    EditText mEtPhoneNumber;
    @Bind(R.id.et_verify_code)
    EditText mEtVerifyCode;
    @Bind(R.id.btn_commit_changes)
    ActionProcessButton mBtnCommitChanges;
    @Restore(BundleKey.ABSAUTHENTRY)
    AbsAuthEntry mAuthEntry;

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mBtnCommitChanges.setMode(ActionProcessButton.Mode.ENDLESS);
        mBtnGetVerify.setText(getString(R.string.login_hint_verification));
        setTitle(R.string.bind_account);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bind_account;
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
        call(new JobVerifyCode(content, JobVerifyCode.TYPE_BIND,mAuthEntry.getType()), new SimpleCallback<BaseEntity>() {
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
    protected Button bindRetryVerifyCodeButton() {
        return mBtnGetVerify;
    }

    @OnClick(R.id.btn_commit_changes)
    protected void onConfirm(View view){
        String phoneNumber = mEtPhoneNumber.getText().toString();
        String verifyCode = mEtVerifyCode.getText().toString();
        boolean ok = true;
        if(TextUtils.isEmpty(phoneNumber)){
            mEtPhoneNumber.setError(mEtPhoneNumber.getHint());
            ok=false;
        }
        if(TextUtils.isEmpty(verifyCode)){
            mEtVerifyCode.setError(mEtVerifyCode.getHint());
            ok = false;
        }
        if(!ok){
            return;
        }
        mBtnCommitChanges.setEnabled(false);
        mBtnCommitChanges.setProgress(10);
        call(new JobOtherLoginbind(mAuthEntry,phoneNumber, verifyCode), new SimpleCallback<BaseEntity<LoginEntity>>() {
            @Override
            public void onError(Throwable e) {
                mBtnCommitChanges.setEnabled(true);
                mBtnCommitChanges.setProgress(0);
                SnackBar.make(getActivity(), e.getMessage()).show();
            }

            @Override
            public void onSuccess(BaseEntity<LoginEntity> loginEntityBaseEntity) {
                if(loginEntityBaseEntity.getErrCode()==1){
                    GetUserInfo();
                }else if(loginEntityBaseEntity.getErrCode()==2){
                    mBtnCommitChanges.setProgress(0);
                    mBtnCommitChanges.setEnabled(true);
                    SnackBar.make(getContext(),getString(R.string.login_bind_account_ok)).show();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(BundleKey.ABSAUTHENTRY,mAuthEntry);
                    bundle.putString(BundleKey.PHONE, phoneNumber);
                    bundle.putString(BundleKey.VERIFY_CODE,verifyCode);
                    go2(SetPasswordFragment.class,bundle);
                }
            }
        });
    }


    public void GetUserInfo() {
        call(new JobGetUserInfo(), new SimpleCallback<BaseEntity<JobGetUserInfo>>() {


            @Override
            public void onError(Throwable e) {
                mBtnCommitChanges.setProgress(0);
                mBtnCommitChanges.setEnabled(true);
                SnackBar.make(getActivity(), e.getMessage()).show();
            }

            @Override
            public void onSuccess(BaseEntity<JobGetUserInfo> jobGetUserInfoBaseEntity) {
                mBtnCommitChanges.setProgress(0);
                mBtnCommitChanges.setEnabled(true);
                go(NavigatorActivity.class);
            }
        });
    }

}
