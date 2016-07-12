package com.andcup.android.app.integralwall.view.cash.qb;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.andcup.android.app.integralwall.view.base.GateDialogFragment;
import com.andcup.android.app.integralwall.view.guide.NewUserTaskFragment;
import com.yl.android.cpa.R;

import butterknife.Bind;

/**
 * Created by Amos on 2015/11/3.
 */
public class DoTaskFragment extends GateDialogFragment {

    @Bind(R.id.tv_content)
    TextView mTvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_qb_new_user_task;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTvContent.setText(Html.fromHtml(getString(R.string.qb_exchange_failed)));
    }

    @Override
    protected void onOk() {
        go2(NewUserTaskFragment.class, null);
        dismissAllowingStateLoss();
    }
}
