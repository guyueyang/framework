package com.andcup.android.app.integralwall.view.usercenter.setting;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.config.ConfigureProvider;
import com.andcup.android.app.integralwall.datalayer.tools.DeviceUtils;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.integralwall.commons.tools.AndroidUtils;
import com.andcup.sdk.push.PushSdk;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UpdateConfig;
import com.yl.android.cpa.R;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Amos on 2015/9/9.
 */
public class AboutFragment extends BaseFragment {

    @Bind(R.id.tv_version)
    TextView mTvVersion;
    @Bind(R.id.iv_about_head)
    ImageView mivHead;
    @Bind(R.id.iv_logo)
    ImageView mIvLogo;
    @Bind(R.id.et_token)
    EditText mEtToken;

    int mClickCount = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void afterActivityCreate(Bundle afterActivityCreate) {
        super.afterActivityCreate(afterActivityCreate);
        setTitle(R.string.about_me);

        mTvVersion.setText(getString(R.string.version, DeviceUtils.getVerName(getActivity())));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height=width/4;
        ViewGroup.LayoutParams layoutParams = mivHead.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        mivHead.setLayoutParams(layoutParams);
    }

    @OnClick(R.id.iv_logo)
    public void onClickLogo(){
        if(mClickCount++ >= 5){
            mTvVersion.setText(
                    getString(R.string.version, DeviceUtils.getVerName(getActivity()))
                    + " c : " + UpdateConfig.getChannel(getActivity())
                    + " i : " + UserProvider.getInstance().getInviteUid());
            mEtToken.setVisibility(View.VISIBLE);
            mEtToken.setText(PushSdk.getInstance().getToken());
        }
    }
}
