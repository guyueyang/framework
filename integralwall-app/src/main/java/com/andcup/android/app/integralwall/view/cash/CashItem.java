package com.andcup.android.app.integralwall.view.cash;

import com.andcup.android.app.integralwall.datalayer.model.CashingItemEntity;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.cash.alipay.AlipayFragment;
import com.andcup.android.app.integralwall.view.cash.bank.BankCardFragment;
import com.andcup.android.app.integralwall.view.cash.phone.PhoneFareFragment;
import com.yl.android.cpa.R;

import java.io.Serializable;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/22.
 */
public enum CashItem implements Serializable{

    PHONEFARE(CashingItemEntity.TYPE_FARE, PhoneFareFragment.class, R.string.telephone_fare, R.drawable.bg_banner_phone_fare),
    ALIPAY(CashingItemEntity.TYPE_CASHING_ALIPAY, AlipayFragment.class, R.string.exchange_money,  R.drawable.bg_banner_alipay),
    BANKCARD(CashingItemEntity.TYPE_CASHING_BANK, BankCardFragment.class, R.string.exchange_money_bank,  R.drawable.bg_banner_bank_card);

    int mType;
    Class<? extends BaseFragment> mClazz;
    int mTitle;
    int mBannerId;

    CashItem(int type, Class<? extends BaseFragment> clazz, int title, int bannerId){
        mType  = type;
        mClazz = clazz;
        mBannerId = bannerId;
        mTitle = title;
    }
}
