package com.andcup.android.app.integralwall.tools;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.andcup.android.app.integralwall.IntegralWallApplication;
import com.andcup.android.app.integralwall.view.IntegralWallDialogFragment;
import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.frame.view.RxFragment;
import com.andcup.android.frame.view.navigator.DialogFragmentNavigator;
import com.yl.android.cpa.R;

import java.io.Serializable;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/23.
 */
public class Dialog implements Serializable{

    public interface Provider{
        Dialog build(Class<? extends RxFragment> clazz, Bundle bundle);
    }

    public static Provider SNAPSHOT = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle)
                    .setBackground(R.color.transparent)
                    .cancel(false, null)
                    .ok(false, null)
                    .fitWidth(false)
                    .cancelAble(false)
                    .to(clazz);
            return helper;
        }
    };

    public static Provider INVITE_CODE = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle)
                    .setBackground(R.color.transparent)
                    .cancel(false, null)
                    .ok(false, null)
                    .fitWidth(false)
                    .to(clazz);
            return helper;
        }
    };

    public static Provider EXCHANGE_DISABLED = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle).cancel(false, null).cancelAble(false).to(clazz);
            return helper;
        }
    };

    public static Provider RUN_TASK = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle).cancel(false, null).cancelAble(true).to(clazz);
            return helper;
        }
    };


    public static Provider BIND_PHONE = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle).cancel(false, null).cancelAble(true).to(clazz);
            return helper;
        }
    };

    public static Provider CONTENT = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle).cancel(false, null).cancelAble(true).to(clazz);
            return helper;
        }
    };

    public static Provider ACCOUNT_DISABLED = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle).cancel(false, null).cancelAble(false).to(clazz);
            return helper;
        }
    };

    public static Provider CHECK = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle).ok(true,
                    IntegralWallApplication.getInstance().getString(R.string.retry))
                    .cancelAble(false)
                    .to(clazz);
            return helper;
        }
    };

    public static Provider CALENDAR = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle).ok(false, null).cancel(false, null).cancelAble(true).to(clazz);
            return helper;
        }
    };

    public static Provider ANOTHER_LOGIN = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle).ok(true, null).cancel(false, null).cancelAble(false).to(clazz);
            return helper;
        }
    };

    public static Provider CARD = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle).ok(false, null).cancel(false, null).fitWidth(false).to(clazz);
            return helper;
        }
    };

    public static Provider LOGIN_OUT = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle).ok(false, null).cancel(false, null).to(clazz);
            return helper;
        }
    };

    public static Provider SHARE = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle).ok(false, null).cancel(false, null).to(clazz);
            return helper;
        }
    };

    public static Provider QB = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle)
                    .ok(true, null)
                    .to(clazz);
            return helper;
        }
    };

    public static Provider EDIT = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle)
                    .ok(true, null)
                    .to(clazz);
            return helper;
        }
    };

    public static Provider RULES = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle).ok(false, null).cancel(false, null).to(clazz);
            return helper;
        }
    };

    public static Provider QB_TASK = new Provider() {
        @Override
        public Dialog build(Class<? extends RxFragment> clazz, Bundle bundle) {
            Dialog helper = new Dialog();
            helper.with(bundle)
                    .ok(true, IntegralWallApplication.getInstance().getResources().getString(R.string.do_task))
                    .cancel(false, null)
                    .to(clazz);
            return helper;
        }
    };

    boolean mUseOK = true;
    boolean mUseCancel = true;
    boolean mCancelAble = true;
    boolean mFitWidth = true;
    Bundle  mBundle;
    Class<? extends RxFragment> mClazz;
    String  mOKText;
    String  mCancelText;
    int     mBackgroundId = -1;

    public Dialog ok(boolean used, String text){
        mUseOK = used;
        mOKText = text;
        return this;
    }

    public Dialog cancel(boolean used, String text){
        mUseCancel = used;
        mCancelText = text;
        return this;
    }

    public Dialog okText(String mOK) {
        this.mOKText = mOK;
        return this;
    }

    public Dialog cancelText(String mCancel) {
        this.mCancelText = mCancel;
        return this;
    }

    public Dialog with(Bundle bundle){
        mBundle = bundle;
        return this;
    }

    public Dialog to(Class<? extends RxFragment> clazz){
        mClazz = clazz;
        return this;
    }

    public Dialog cancelAble(boolean cancelAble){
        mCancelAble = cancelAble;
        return this;
    }

    public Dialog fitWidth(boolean fitWidth){
        mFitWidth = fitWidth;
        return this;
    }

    public Dialog setBackground(int resourceId){
        mBackgroundId = resourceId;
        return this;
    }

    public void show(FragmentManager fm){
        DialogFragmentNavigator dfn = new DialogFragmentNavigator(fm);
        if(null == mBundle ){
            mBundle = new Bundle();
        }
        mBundle.putSerializable(BundleKey.TARGET_CLAZZ, mClazz);
        mBundle.putBoolean(BundleKey.OK, mUseOK);
        mBundle.putBoolean(BundleKey.CANCEL, mUseCancel);
        mBundle.putBoolean(BundleKey.CANCEL_ABLE, mCancelAble);
        mBundle.putBoolean(BundleKey.BOTTOM_AND_FIT_WIDTH, mFitWidth);
        if(!TextUtils.isEmpty(mOKText)){
            mBundle.putString(BundleKey.OK_TEXT, mOKText);
        }
        if(!TextUtils.isEmpty(mCancelText)){
            mBundle.putString(BundleKey.CANCEL_TEXT, mCancelText);
        }
        if(mBackgroundId != -1){
            mBundle.putString(BundleKey.RESOURCE_ID, String.valueOf(mBackgroundId));
        }
        dfn.with(mBundle).to(IntegralWallDialogFragment.class).go();
    }

    @Deprecated
    public static void showOK(FragmentManager fm, Class<? extends RxFragment> clazz, Bundle bundle){
        Dialog helper = new Dialog();
        helper.with(bundle).ok(true, null).cancel(false, null).to(clazz).show(fm);
    }
    @Deprecated
    public static void showCancel(FragmentManager fm, Class<? extends RxFragment> clazz, Bundle bundle){
        Dialog helper = new Dialog();
        helper.with(bundle).ok(false, null).cancel(true, null).to(clazz).show(fm);
    }
    @Deprecated
    public static void showNoAction(FragmentManager fm, Class<? extends RxFragment> clazz, Bundle bundle){
        Dialog helper = new Dialog();
        helper.with(bundle).ok(false, null).cancel(false, null).to(clazz).show(fm);
    }

    @Deprecated
    public static void show(FragmentManager fm, Class<? extends RxFragment> clazz, Bundle bundle){
        Dialog helper = new Dialog();
        helper.with(bundle).to(clazz).show(fm);
    }

}
