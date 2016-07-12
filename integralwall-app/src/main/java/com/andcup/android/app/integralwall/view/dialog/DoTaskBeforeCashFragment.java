package com.andcup.android.app.integralwall.view.dialog;

import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;

import com.andcup.android.app.integralwall.view.task.Task2Fragment;
import com.yl.android.cpa.R;


/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/5.
 */
public class DoTaskBeforeCashFragment extends ContentDialogFragment {

    @Override
    protected void onOk() {
        go2(Task2Fragment.class, null);
        dismissAllowingStateLoss();
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTvContent.setGravity(Gravity.CENTER);
        mTvContent.setText(Html.fromHtml(getString(R.string.please_do_task)));
    }
}
