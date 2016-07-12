package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.frame.datalayer.exception.JobException;

import java.io.IOException;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/22.
 */
public class JobCheckCashingBank extends IntegralWallJob<BaseEntity>{

    String mPhoneNumber;
    String mCashingItemId;
    String mName;
    String mBankAccount;
    String mBankName;

    public JobCheckCashingBank(String account, String bankName, String phoneNumber, String cashItemId, String name){
        mPhoneNumber = phoneNumber;
        mCashingItemId = cashItemId;
        mName = name;
        mBankAccount = account;
        mBankName = bankName;
    }


    @Override
    public BaseEntity start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long time  = getTimestamp();
        String sign = md5(uid + mCashingItemId + mBankAccount + mPhoneNumber + time + getKey());
        return apis().checkCashingBank(uid, mName, mCashingItemId, mBankName, mBankAccount, mPhoneNumber, time, sign).execute().body();
    }
}
