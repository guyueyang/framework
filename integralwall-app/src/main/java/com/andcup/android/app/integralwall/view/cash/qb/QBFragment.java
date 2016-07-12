package com.andcup.android.app.integralwall.view.cash.qb;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobCheckQb;
import com.andcup.android.app.integralwall.datalayer.model.QbEntity;
import com.andcup.android.app.integralwall.event.QBEvent;
import com.andcup.android.app.integralwall.event.ScoreEvent;
import com.andcup.android.app.integralwall.event.ShareEvent;
import com.andcup.android.app.integralwall.view.base.GateDialogFragment;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;


import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

/**
 * Created by Amos on 2015/11/4.
 */
public class QBFragment extends GateDialogFragment {

    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.et_qq)
    EditText mEtQQ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_qb_exchange;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTvContent.setText(Html.fromHtml(getString(R.string.qb_exchange_info)));
    }

    @Override
    protected void onOk() {
        String qqNumber = mEtQQ.getText().toString();
        if(TextUtils.isEmpty(qqNumber)){
            mEtQQ.setError(getString(R.string.hint_qq_number));
            return;
        }

        showLoading();
        call(new JobCheckQb(qqNumber), new SimpleCallback<QbEntity>() {
            @Override
            public void onSuccess(QbEntity jobEntity) {
                SnackBar.make(getContext(), Html.fromHtml(jobEntity.getMessage())).show();
                EventBus.getDefault().post(new QBEvent(0));
                hideLoading();
                dismissAllowingStateLoss();
            }

            @Override
            public void onError(Throwable e) {
                hideLoading();
                SnackBar.make(getContext(), Html.fromHtml(e.getMessage())).show();
            }
        });
    }

    @Override
    protected void onCancel(){
        dismissAllowingStateLoss();
    }
}
