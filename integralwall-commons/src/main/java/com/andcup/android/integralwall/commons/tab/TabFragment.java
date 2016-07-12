package com.andcup.android.integralwall.commons.tab;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.andcup.android.frame.view.RxFragment;
import com.andcup.android.integralwall.commons.R;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/11.
 */
public abstract class TabFragment extends RxFragment{

    protected TabLayout  mTlTab;
    protected ViewPager  mVpTab;
    protected TabAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tabs;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTlTab = findViewById(R.id.tl_tab);
        mVpTab = findViewById(R.id.vp_tab);
        Tab[] tabs = getTabs();
        mAdapter = new TabAdapter(getActivity(), getChildFragmentManager(), tabs);
        mVpTab.setAdapter(mAdapter);
        mTlTab.setupWithViewPager(mVpTab);
        mAdapter.notifyDataSetChanged();
    }

    protected abstract Tab[] getTabs();

}
