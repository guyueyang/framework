package com.andcup.android.app.integralwall.view.dialog;

import android.os.Bundle;
import android.widget.TextView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.tools.DeviceUtils;
import com.andcup.android.app.integralwall.event.ReCheckEvent;
import com.andcup.android.app.integralwall.tools.AppHelper;
import com.andcup.android.app.integralwall.view.base.GateDialogFragment;
import com.andcup.android.frame.view.annotations.Restore;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/1.
 */
public class BlackFragment extends GateDialogFragment{

    public static final int SIM_NOT_READY = 0;

    @Bind(R.id.tv_content)
    TextView mTvContent;

    @Restore(BundleKey.CONTENT)
    String mContent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_black;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTvContent.setText(mContent);
    }

    @Override
    protected void onOk() {
        EventBus.getDefault().post(new ReCheckEvent());
        dismissAllowingStateLoss();
    }

    @Override
    protected void onCancel() {
        AppHelper.exit();
    }
}
