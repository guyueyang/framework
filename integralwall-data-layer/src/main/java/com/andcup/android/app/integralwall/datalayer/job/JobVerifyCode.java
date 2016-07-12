package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/10.
 */
public class JobVerifyCode extends IntegralWallJob<BaseEntity> {

    public static final int TYPE_FIND_PASSWORD = 1;
    public static final int TYPE_REGISTER = 2;
    public static final int TYPE_BIND=6;

    private String mPhoneNumber;
    private int    mType;
    private int mBindType;

    public JobVerifyCode(String phoneNumber, int type,int bindType){
        mPhoneNumber=phoneNumber;
        mType=type;
        mBindType=bindType;
    }

    @Override
    public BaseEntity start() throws IOException {
        long time=getTimestamp();
        return apis().verifyCode(mPhoneNumber,mType,mBindType,time,md5(mPhoneNumber+mType+time+getKey()))
                .execute()
                .body();
    }
}
