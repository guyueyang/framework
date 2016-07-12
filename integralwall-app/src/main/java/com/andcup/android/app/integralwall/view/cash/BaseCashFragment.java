package com.andcup.android.app.integralwall.view.cash;


import android.os.Bundle;
import android.text.TextUtils;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.event.CashItemEvent;
import com.andcup.android.app.integralwall.tools.Dialog;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.dialog.BindPhoneNumberFragment;
import com.andcup.android.app.integralwall.view.dialog.DisabledExchangeFragment;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.Subscribe;


/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/5.
 */
public abstract class BaseCashFragment extends BaseFragment {

    protected CashItemEvent mCashItem;

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        try{
            boolean isDisabled = UserProvider.getInstance().getUserInfo().isDisableExchange();
            if(isDisabled){
                Dialog.EXCHANGE_DISABLED.build(DisabledExchangeFragment.class, null).show(getChildFragmentManager());
            }
        }catch (Exception e){

        }
    }

    @Subscribe
    public void onCashItemChangedEvent(CashItemEvent cashItemEvent){
        mCashItem = cashItemEvent;
        onCashItemChanged(cashItemEvent);
    }

    protected boolean bindPhone(){
        if(TextUtils.isEmpty(UserProvider.getInstance().getUserInfo().getMobile())){
            Bundle bundle = new Bundle();
            bundle.putSerializable(BundleKey.CONTENT, getString(R.string.bind_phone_number));
            Dialog.BIND_PHONE
                    .build(BindPhoneNumberFragment.class, bundle)
                    .okText(getString(R.string.to_bind_phone_number))
                    .show(getChildFragmentManager());
            return false;
        }
        return true;
    }

    public abstract void onCashItemChanged(CashItemEvent cashItemEvent);
}
