package com.andcup.android.app.integralwall.view.dialog;

import android.os.Bundle;
import android.view.Gravity;

import com.andcup.android.app.integralwall.tools.AppHelper;


/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/30.
 */
public class AnotherLoginFragment extends ContentDialogFragment {

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTvContent.setGravity(Gravity.CENTER);
    }

    @Override
    protected void onOk() {
        dismissAllowingStateLoss();
        AppHelper.loginOut();
    }
}
