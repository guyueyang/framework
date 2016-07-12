package com.andcup.android.app.integralwall.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.andcup.android.app.integralwall.view.base.BaseDialogFragment;
import com.yl.android.cpa.R;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/25.
 */
public class LoadingDialogFragment extends BaseDialogFragment{

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_loading;
    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppThemeLoading);
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);
        fitContent();
    }

    private void fitContent(){
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.height  = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width   = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(layoutParams);
    }

}
