package com.andcup.android.app.integralwall.view.family;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.tools.Dialog;
import com.andcup.android.app.integralwall.view.base.BaseActionBar;
import com.andcup.android.app.integralwall.view.family.member.MemberFragment;
import com.andcup.android.app.integralwall.view.family.raiders.RaidersFragment;
import com.andcup.android.app.integralwall.view.family.reward.RewardFragment;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.tab.Tab;
import com.andcup.android.integralwall.commons.tab.TabFragment;
import com.yl.android.cpa.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/10.
 */
public class FamilyFragment extends TabFragment {

    @Restore(BundleKey.TITLE)
    String mTitle;

    @Bind(R.id.iv_back)
    ImageView mIvBack;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_family;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        if(TextUtils.isEmpty(mTitle)){
            mIvBack.setVisibility(View.GONE);
        }else{
            mIvBack.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected Tab[] getTabs() {
        Tab member  = new Tab(MemberFragment.class, getString(R.string.member));
        Tab rewards = new Tab(RewardFragment.class, getString(R.string.rewards));
        Tab raiders = new Tab(RaidersFragment.class, getString(R.string.raiders));
        return new Tab[]{member, rewards, raiders};
    }

    @OnClick(R.id.btn_card)
    public void onCardClick(){
        Dialog.CARD.build(MineCardFragment.class, null).show(getChildFragmentManager());
    }

    @OnClick(R.id.iv_back)
    public void onBackClick(){
        getActivity().finish();
    }
}
