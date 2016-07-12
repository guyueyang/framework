package com.andcup.android.app.integralwall.view.dialog;

import android.os.Bundle;
import android.view.Gravity;

import com.yl.android.cpa.R;


/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/5.
 */
public class DisabledExchangeFragment extends ContentDialogFragment {

    @Override
    protected void onOk() {
        super.onOk();
        dismissAllowingStateLoss();
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTvContent.setText(getString(R.string.disable_exchange));
        mTvContent.setGravity(Gravity.CENTER);
    }
}
