package com.andcup.android.app.integralwall.view.dialog;

import android.os.Bundle;
import android.widget.TextView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.view.base.GateDialogFragment;
import com.andcup.android.frame.view.annotations.Restore;
import com.yl.android.cpa.R;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/5.
 */
public class ContentDialogFragment extends GateDialogFragment {

    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Restore(BundleKey.CONTENT)
    String   mContent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dialog_content;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTvContent.setText(mContent);
    }

    @Override
    protected void onOk() {
        super.onOk();
        dismissAllowingStateLoss();
    }
}
