package com.andcup.android.app.integralwall.datalayer.model.base;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.exception.AccountDisableException;
import com.andcup.android.app.integralwall.datalayer.exception.AnotherLoginException;
import com.andcup.android.frame.datalayer.job.JobEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

/**
 * Created by Amos on 2015/9/3.
 */
public class BaseListEntry<T extends Serializable> extends JobEntity<T> implements Serializable {

    @JsonProperty("status")
    private int mStatus;
    @JsonProperty("message")
    private String mMessage;
    @JsonProperty("data")
    private BaseListData<T> mData;

    public BaseListData<T> getBaseListData() {
        return mData;
    }

    @Override
    public int getErrCode() {
        return mStatus;
    }

    @Override
    public String getMessage() {
        return mMessage;
    }

    @Override
    public void throwIfException() {
        if (mStatus < 0) {
            if (mStatus == IntegralWallJob.LOGIN_AT_ANOTHER) {
                EventBus.getDefault().post(new AnotherLoginException());
            } else if (mStatus == IntegralWallJob.ACCOUNT_DISABLED) {
                EventBus.getDefault().post(new AccountDisableException(mMessage));
            }
            throw new RuntimeException(mMessage);
        }
    }

    public BaseListData<T> data() {
        return mData;
    }

    @Override
    public T body() {
        return null;
    }

}
