package com.andcup.android.app.integralwall.view.navigator;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/17.
 */
public class NavigatorAdapter extends FragmentStatePagerAdapter {
    // items.
    public static NavigatorItem NAVIGATORS[] = new NavigatorItem[]{
            NavigatorItem.Home, NavigatorItem.Task, NavigatorItem.Family, NavigatorItem.User
    };

    List<Fragment> mFragments = new ArrayList<>();
    Context mContext;

    public NavigatorAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position < mFragments.size()){
            return mFragments.get(position);
        }
        Class<? extends Fragment> targetClass = NAVIGATORS[position].getClazz();
        try {
            Fragment fragment = targetClass.newInstance();
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
        return NAVIGATORS == null ? 0 : NAVIGATORS.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(NAVIGATORS[position].getTitle());
    }
}
