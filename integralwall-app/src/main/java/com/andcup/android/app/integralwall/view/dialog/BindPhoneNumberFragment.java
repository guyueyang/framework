package com.andcup.android.app.integralwall.view.dialog;


import android.os.Bundle;
import android.view.Gravity;

import com.andcup.android.app.integralwall.tools.Dialog;
import com.andcup.android.app.integralwall.view.usercenter.edit.EditBindAccountFragment;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/5.
 */
public class BindPhoneNumberFragment extends ContentDialogFragment {

    @Override
    protected void onOk() {
        super.onOk();
        Dialog.EDIT.build(EditBindAccountFragment.class, null).show(getActivity().getSupportFragmentManager());
        dismissAllowingStateLoss();
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTvContent.setGravity(Gravity.CENTER);
    }
}
