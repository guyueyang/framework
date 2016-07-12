package com.andcup.android.app.integralwall.view.usercenter.edit;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobEditPassword;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.base.GateDialogFragment;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;
import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/24.
 */
public class EditPasswordFragment extends GateDialogFragment {

    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.et_new_password)
    EditText mEtNewPassword;
    @Bind(R.id.et_new_password_ok)
    EditText mEtNewPasswordOk;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_password;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
    }

    @Override
    protected void onOk(){
        String oldPassword = mEtPassword.getText().toString();
        String newPassword = mEtNewPassword.getText().toString();
        String newPasswordOK = mEtNewPasswordOk.getText().toString();



        if (TextUtils.isEmpty(oldPassword)) {
            SnackBar.make(getActivity(), getString(R.string.error_old_password)).show();
            return;
        }

        if (TextUtils.isEmpty(newPassword)) {
            SnackBar.make(getActivity(), getString(R.string.error_password)).show();
            return;
        }

        String regex = "^[a-z0-9A-Z_)(*&^%$#@!~`./.,<>?{}:;《》？，：;'。！!?.,]+$";
        if(!newPassword.matches(regex)){
            SnackBar.make(getContext(),getString(R.string.limits_char_numbers)).show();
            return;
        }
        if (newPassword.length() < 6) {
            SnackBar.make(getActivity(), getString(R.string.error_Length_limit_small)).show();
            return;
        }
        if (newPassword.length() > 22) {
            SnackBar.make(getActivity(), getString(R.string.error_length_limit_big)).show();
            return;
        }

        if (TextUtils.isEmpty(newPasswordOK) || !newPassword.equals(newPasswordOK)) {
            SnackBar.make(getActivity(), getString(R.string.error_not_same_password)).show();
            return;
        }
        showLoading();
        call(new JobEditPassword(oldPassword,newPassword), new SimpleCallback<BaseEntity>() {
            @Override
            public void onError(Throwable e) {
                SnackBar.make(getContext(),e.getMessage()).show();
                hideLoading();
            }

            @Override
            public void onSuccess(BaseEntity baseEntity) {
                SnackBar.make(getContext(),baseEntity.getMessage()).show();
                hideLoading();
                dismissAllowingStateLoss();
            }
        });
    }

    @Override
    protected void onCancel(){
        dismissAllowingStateLoss();
    }

}