package com.andcup.android.integralwall.commons.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/11.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    List<Fragment> mFragments = new ArrayList<>();
    Tab[]   mTabs;
    Context mContext;

    public TabAdapter(Context context, FragmentManager fm, Tab[] tabList) {
        super(fm);
        mTabs = tabList;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position < mFragments.size()){
            return mFragments.get(position);
        }
        Class<? extends Fragment> targetClass = mTabs[position].getClazz();
        try {
            Fragment fragment = targetClass.newInstance();
            fragment.setArguments(mTabs[position].getBundle());
            mFragments.add(fragment);
            return fragment;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTabs == null ? 0 : mTabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs[position].getTitle();
    }
}
