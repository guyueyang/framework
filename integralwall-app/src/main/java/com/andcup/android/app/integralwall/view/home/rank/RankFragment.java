package com.andcup.android.app.integralwall.view.home.rank;

import android.os.Bundle;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.tools.Dialog;
import com.andcup.android.app.integralwall.view.dialog.ContentDialogFragment;
import com.andcup.android.integralwall.commons.tab.Tab;
import com.andcup.android.integralwall.commons.tab.TabFragment;
import com.yl.android.cpa.R;

import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/23.
 */
public class RankFragment extends TabFragment{

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rank;
    }

    @Override
    protected Tab[] getTabs() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.RANK, Rank.DAY3);
        Tab week  = new Tab(RankChildFragment.class, getString(R.string.rank_week), bundle);

        bundle = new Bundle();
        bundle.putSerializable(BundleKey.RANK, Rank.DAY7);
        Tab month = new Tab(RankChildFragment.class, getString(R.string.rank_month), bundle);
        return new Tab[]{week, month};
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_back)
    public void finish(){
        getActivity().finish();
    }

    @OnClick(R.id.tv_rule)
    public void onRuleClick(){
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.CONTENT, getString(R.string.rank_rule_detail));
        Dialog.CONTENT.build(ContentDialogFragment.class, bundle).show(getChildFragmentManager());
    }
}
