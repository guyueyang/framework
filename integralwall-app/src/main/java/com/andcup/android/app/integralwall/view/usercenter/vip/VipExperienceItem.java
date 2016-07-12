package com.andcup.android.app.integralwall.view.usercenter.vip;

/**
 * Created by Administrator on 2016/3/26.
 */
public class VipExperienceItem<T> {

    T mT;

    public VipExperienceItem(T t) {
        mT = t;
    }

    public T getData() {
        return mT;
    }
}
