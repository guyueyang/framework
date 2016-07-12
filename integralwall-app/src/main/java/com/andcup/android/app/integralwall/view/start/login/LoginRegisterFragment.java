package com.andcup.android.app.integralwall.view.start.login;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.tab.Tab;
import com.andcup.android.integralwall.commons.tab.TabFragment;
import com.yl.android.cpa.R;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/17.
 */
@Deprecated
public class LoginRegisterFragment extends TabFragment {

    @Restore(BundleKey.JUMP)
    String mJump;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_register;
    }

    @Override
    protected Tab[] getTabs() {
        Tab LoginFragment = new Tab(LoginFragment.class, getString(R.string.login));
        Tab registerFragment = new Tab(RegisterFragment.class, getString(R.string.login_register));
        return new Tab[]{LoginFragment, registerFragment};
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    mVpTab.setCurrentItem(Integer.parseInt(mJump), true);
                } catch (Exception e) {

                }
            }
        }, 20);

    }

    @OnClick(R.id.iv_back)
    public void close(){
        getActivity().finish();
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
