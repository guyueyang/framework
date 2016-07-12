package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/25.
 */
public class CheckQbEntity extends BaseEntity{

    public static final int QB_EXCHANGE_NOT_FINISH = -3;
    public static final int QB_EXCHANGE_READY = 1;

    @Override
    public void throwIfException() {
        if(getErrCode() != QB_EXCHANGE_READY && getErrCode() != QB_EXCHANGE_NOT_FINISH){
            super.throwIfException();
        }
    }
}
