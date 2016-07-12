package com.andcup.android.app.integralwall.view.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobInviteCode;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.tools.Sharef;
import com.andcup.android.app.integralwall.view.base.GateDialogFragment;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/10.
 */
public class InviteFragment extends GateDialogFragment {

    @Bind(R.id.et_invite_code)
    EditText mEtInviteCode;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invite;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        Sharef.setInviteNotify(true);
    }

    @OnClick(R.id.tv_cancel)
    public void onCancel(){
        dismissAllowingStateLoss();
    }

    @OnClick(R.id.tv_ok)
    public void onOK(){
        String inviteCode = mEtInviteCode.getText().toString();
        if(TextUtils.isEmpty(inviteCode)) {
            mEtInviteCode.setError(getString(R.string.input_invite_code));
            return;
        }
        showLoading();
        call(new JobInviteCode(inviteCode), new SimpleCallback<BaseEntity>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                hideLoading();
                SnackBar.make(getContext(), e.getMessage()).show();
            }

            @Override
            public void onSuccess(BaseEntity baseEntity) {
                super.onSuccess(baseEntity);
                UserProvider.getInstance().getUserInfo().setInviteUid(inviteCode);
                dismissAllowingStateLoss();
            }
        });
    }
}
