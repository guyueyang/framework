package com.andcup.android.app.integralwall.view.usercenter.invite;

import com.andcup.android.integralwall.commons.tab.Tab;
import com.andcup.android.integralwall.commons.tab.TabFragment;
import com.yl.android.cpa.R;

import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/26.
 */
public class InviteCodeFragment extends TabFragment {
    @Override
    protected Tab[] getTabs() {
        Tab MiInviteCodeFragment = new Tab(MiInviteCodeFragment.class, getString(R.string.mi_invite_code));
        Tab WriteInviteCodeFragment = new Tab(WriteInviteCodeFragment.class, getString(R.string.input_invite_code));
        return new Tab[]{MiInviteCodeFragment, WriteInviteCodeFragment};
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invite_code;
    }

    @OnClick(R.id.iv_back)
    public void close(){
        getActivity().finish();
    }
}
