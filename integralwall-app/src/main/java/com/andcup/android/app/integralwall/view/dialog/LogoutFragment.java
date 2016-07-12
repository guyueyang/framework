package com.andcup.android.app.integralwall.view.dialog;

import com.andcup.android.app.integralwall.tools.AppHelper;
import com.andcup.android.app.integralwall.view.base.GateDialogFragment;
import com.yl.android.cpa.R;

import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/29.
 */
public class LogoutFragment extends GateDialogFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_out;
    }

    @OnClick(R.id.btn_exit)
    public void onExit(){
        AppHelper.loginOut();
    }

    @OnClick(R.id.btn_cancel)
    public void onCaccel(){
        dismissAllowingStateLoss();
    }
}
