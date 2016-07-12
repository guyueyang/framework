package com.andcup.android.app.integralwall.datalayer.exception;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/5.
 */
public class BizException {

    String mValue;

    public BizException(String value){
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
