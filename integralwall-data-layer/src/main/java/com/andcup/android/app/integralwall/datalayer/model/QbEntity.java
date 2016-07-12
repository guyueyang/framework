package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/25.
 */
public class QbEntity extends BaseEntity {

    @Override
    public void throwIfException() {
        if(getErrCode() != 1 && getErrCode() != -7){
            super.throwIfException();
        }
    }
}
