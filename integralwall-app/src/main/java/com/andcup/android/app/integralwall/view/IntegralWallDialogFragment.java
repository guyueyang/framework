package com.andcup.android.app.integralwall.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.event.DialogEvent;
import com.andcup.android.app.integralwall.view.base.BaseDialogFragment;
import com.andcup.android.frame.view.RxFragment;
import com.andcup.android.frame.view.annotations.Restore;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/23.
 */
public class IntegralWallDialogFragment extends BaseDialogFragment {

    @Restore(BundleKey.TARGET_CLAZZ)
    Class<? extends RxFragment> mClazz;
    @Restore(BundleKey.OK)
    boolean mUseOK;
    @Restore(BundleKey.CANCEL)
    boolean mUseCancel;
    @Restore(BundleKey.OK_TEXT)
    String mOKText;
    @Restore(BundleKey.CANCEL_TEXT)
    String mCancelText;
    @Bind(R.id.btn_cancel)
    Button mBtnCancel;
    @Bind(R.id.btn_ok)
    Button mBtnOK;
    @Restore(BundleKey.CANCEL_ABLE)
    boolean mCancelAble = true;
    @Restore(BundleKey.BOTTOM_AND_FIT_WIDTH)
    boolean mIsFitWidth = true;
    @Restore(BundleKey.RESOURCE_ID)
    String  mResourceId;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppThemeCommonDialog);
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        if (mCancelAble) {
            getDialog().setCanceledOnTouchOutside(true);
        } else {
            getDialog().setCanceledOnTouchOutside(false);
            getDialog().setCancelable(false);
        }
        if (mIsFitWidth) {
            fitWidth();
        } else {
            fitContent();
        }
        mBtnCancel.setVisibility(mUseCancel ? View.VISIBLE : View.GONE);
        mBtnOK.setVisibility(mUseOK ? View.VISIBLE : View.GONE);
        if (!TextUtils.isEmpty(mOKText)) {
            mBtnOK.setText(mOKText);
        }
        if (!TextUtils.isEmpty(mCancelText)) {
            mBtnCancel.setText(mCancelText);
        }

        go(mClazz, R.id.fr_container, getArguments());
    }

    private int getBackgroundId(){
        if(TextUtils.isEmpty(mResourceId) || mResourceId.equals("-1")){
            return R.color.white;
        }else{
            return Integer.parseInt(mResourceId);
        }
    }

    private void fitContent() {
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(layoutParams);
        getDialog().getWindow().setBackgroundDrawableResource(getBackgroundId());
    }

    private void fitWidth() {
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(layoutParams);
        getDialog().getWindow().setBackgroundDrawableResource(getBackgroundId());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_navigator;
    }

    @OnClick(R.id.btn_cancel)
    public void onCancel() {
        EventBus.getDefault().post(DialogEvent.CANCEL);
    }

    @OnClick(R.id.btn_ok)
    public void onOK() {
        EventBus.getDefault().post(DialogEvent.OK);
    }

    @Subscribe
    public void onReceivedDismiss(DialogEvent dialogEvent) {
        if (dialogEvent == DialogEvent.DISMISS) {
            dismissAllowingStateLoss();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = getChildFragmentManager().getFragments();
        for(int i = 0; null != fragmentList && i< fragmentList.size(); i++){
            fragmentList.get(i).onActivityResult(requestCode, resultCode, data);
        }
    }
}
