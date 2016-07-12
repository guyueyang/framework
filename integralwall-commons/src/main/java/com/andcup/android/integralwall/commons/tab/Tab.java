package com.andcup.android.integralwall.commons.tab;

import android.os.Bundle;

import com.andcup.android.frame.view.RxFragment;

import java.io.Serializable;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/11.
 */
public class Tab implements Serializable{

    Class<? extends RxFragment> mClazz;
    String mTitle;
    Bundle mBundle;

    public Tab(Class<? extends RxFragment> clazz, String title){
        mClazz = clazz;
        mTitle = title;
    }

    public Tab(Class<? extends RxFragment> clazz, String title, Bundle bundle){
        mClazz = clazz;
        mTitle = title;
        mBundle = bundle;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public Class<? extends RxFragment> getClazz() {
        return mClazz;
    }

    public String getTitle() {
        return mTitle;
    }
}
