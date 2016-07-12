package com.andcup.android.app.integralwall.view.cash.alipay;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobCheckCashingAlipay;
import com.andcup.android.app.integralwall.event.CashItemEvent;
import com.andcup.android.app.integralwall.event.ScoreEvent;
import com.andcup.android.app.integralwall.tools.Dialog;
import com.andcup.android.app.integralwall.view.cash.BaseCashFragment;
import com.andcup.android.app.integralwall.view.dialog.DoTaskBeforeCashFragment;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.frame.datalayer.job.JobEntity;
import com.andcup.android.integralwall.commons.tools.Phone;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/22.
 */
public class AlipayFragment extends BaseCashFragment {

    CashItemEvent mCashItem;
    @Bind(R.id.et_alipay_account)
    EditText mEtAlipayAccount;
    @Bind(R.id.et_phone_number)
    EditText mEtPhoneNumber;
    @Bind(R.id.et_real_name)
    EditText mEtRealName;
    @Bind(R.id.btn_action)
    Button mBtnAction;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cash_alipay;
    }

    public void onCashItemChanged(CashItemEvent cashItemEvent){
        mCashItem = cashItemEvent;
        mBtnAction.setEnabled(cashItemEvent.isEnoughScore());
        if(cashItemEvent.isEnoughScore()){
            mBtnAction.setText(getString(R.string.check_cashing));
        }else{
            mBtnAction.setText(getString(R.string.score_not_enough));
        }
    }

    @OnClick(R.id.btn_action)
    public void onClick(){

        if(!bindPhone()){
            return;
        }

        String realName = mEtRealName.getText().toString();
        if(TextUtils.isEmpty(realName)){
            mEtRealName.setError(getString(R.string.error_input_name));
            return;
        }
        String aliCount = mEtAlipayAccount.getText().toString();
        if(TextUtils.isEmpty(aliCount)){
            mEtAlipayAccount.setError(getString(R.string.msg_input_alipay));
            return;
        }
//        if(!Phone.isPhoneNumber(aliCount) && !Phone.isEmail(aliCount)){
//            mEtAlipayAccount.setError(getString(R.string.msg_input_alipay));
//            return;
//        }
        String phoneNumber = mEtPhoneNumber.getText().toString();
        if(TextUtils.isEmpty(phoneNumber)){
            mEtPhoneNumber.setError(getString(R.string.login_error_phone));
            return;
        }
        if(!Phone.isPhoneNumber(phoneNumber)){
            mEtPhoneNumber.setError(getResources().getString(R.string.error_phone_number));
            return;
        }
        call(aliCount.trim(), phoneNumber.trim(), mCashItem.getItem(), realName.trim());
    }

    private void call(String account, String phoneNumber, String id, String name){
        showLoading();
        call(new JobCheckCashingAlipay(account, phoneNumber, id, name), new SimpleCallback<JobEntity>() {
            @Override
            public void onSuccess(JobEntity jobEntity) {
                if(jobEntity.getErrCode() != IntegralWallJob.DO_TASK_BEFORE_CASH_CHECK){
                    SnackBar.make(getActivity(), jobEntity.getMessage()).show();
                    EventBus.getDefault().post(new ScoreEvent(0));
                }else{
                    Dialog.RUN_TASK.build(DoTaskBeforeCashFragment.class, null).okText(getString(R.string.do_task)).show(getChildFragmentManager());
                }
                hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                hideLoading();
                SnackBar.make(getActivity(), e.getMessage()).show();
            }
        });
    }
}
