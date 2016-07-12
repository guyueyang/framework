package com.andcup.android.app.integralwall.view.dialog;

import android.os.Bundle;
import android.view.Gravity;

import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.tools.AppHelper;



/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/5.
 */
public class AccountDisabledFragment extends ContentDialogFragment {

    @Override
    protected void onOk() {
        super.onOk();
        if(UserProvider.getInstance().getUserInfo() != null){
            AppHelper.loginOut();
        }
        dismissAllowingStateLoss();
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTvContent.setGravity(Gravity.CENTER);
    }
}
