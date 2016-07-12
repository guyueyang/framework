package com.andcup.android.app.integralwall.view.usercenter.invite;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobInviteCode;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/26.
 */
public class WriteInviteCodeFragment extends BaseFragment{
    @Bind(R.id.ed_write_invite_code)
    EditText mEdWriteInviteCode;
    @Bind(R.id.btn_submit)
    TextView mBtnSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_write_invite_code;
    }
    @Override
    protected void afterActivityCreate(Bundle afterActivityCreate){
        super.afterActivityCreate(afterActivityCreate);
        String inviteCode=UserProvider.getInstance().getUserInfo().getInviteUid();
        if(!TextUtils.isEmpty(inviteCode) && !inviteCode.equals("0")){
            mEdWriteInviteCode.setText(inviteCode);
            mEdWriteInviteCode.setEnabled(false);
            mBtnSubmit.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.btn_submit)
    protected void onSubmit(){
        String inviteCode=mEdWriteInviteCode.getText().toString();
        if(TextUtils.isEmpty(inviteCode)){
            SnackBar.make(getActivity(),getString(R.string.invite_code_null)).show();
            return;
        }
        if(inviteCode.equals(UserProvider.getInstance().getUid())){
            SnackBar.make(getActivity(), getString(R.string.invite_code_error)).show();
            return;
        }
        showLoading();
        call(new JobInviteCode(inviteCode), new SimpleCallback<BaseEntity>() {
            @Override
            public void onError(Throwable e) {
                SnackBar.make(getActivity(),e.getMessage()).show();
                hideLoading();
            }

            @Override
            public void onSuccess(BaseEntity baseEntity) {
                hideLoading();
                SnackBar.make(getActivity(),getString(R.string.commit_success)).show();
                mEdWriteInviteCode.setEnabled(false);
                mBtnSubmit.setVisibility(View.INVISIBLE);
            }
        });
    }
}
