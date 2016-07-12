package com.andcup.android.app.integralwall.view.cash.bank;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobCheckCashingBank;
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
public class BankCardFragment extends BaseCashFragment{
    CashItemEvent mCashItem;
    @Bind(R.id.et_bank_account)
    EditText mEtBankAccount;
    @Bind(R.id.et_phone_number)
    EditText mEtPhoneNumber;
    @Bind(R.id.et_real_name)
    EditText mEtRealName;
    @Bind(R.id.et_bank_name)
    EditText mEtBankName;
    @Bind(R.id.btn_action)
    Button mBtnAction;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cash_bank;
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
        String bankName = mEtBankName.getText().toString();
        if(TextUtils.isEmpty(bankName)){
            mEtBankName.setError(getString(R.string.error_input_bank_name));
            return;
        }
        String bankAccount = mEtBankAccount.getText().toString();
        if(TextUtils.isEmpty(bankAccount)){
            mEtBankAccount.setError(getString(R.string.msg_input_bank_account));
            return;
        }else if(bankAccount.length() <= 15){
            mEtBankAccount.setError(getString(R.string.msg_input_bank_account_error));
            return;
        }
        String phoneNumber = mEtPhoneNumber.getText().toString();
        if(TextUtils.isEmpty(phoneNumber)){
            mEtPhoneNumber.setError(getString(R.string.login_error_phone));
            return;
        }
        if(!Phone.isPhoneNumber(phoneNumber)){
            mEtPhoneNumber.setError(getResources().getString(R.string.error_phone_number));
            return;
        }
        call(bankAccount.trim(), bankName.trim(), phoneNumber.trim(), mCashItem.getItem(), realName.trim());
    }

    private void call(String account, String bankName, String phoneNumber, String cashItemId, String name){
        showLoading();
        call(new JobCheckCashingBank(account, bankName, phoneNumber, cashItemId, name), new SimpleCallback<JobEntity>() {
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
