package com.andcup.android.app.integralwall.view.start;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.view.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/29.
 */
public class WelcomeAdapter extends FragmentStatePagerAdapter {

    List<Fragment> mFragments = new ArrayList<>();
    Context mContext;
    int mCount = 4;

    public WelcomeAdapter(Context context, FragmentManager fm, int count) {
        super(fm);
        mContext = context;
        mCount = count;
    }

    @Override
    public Fragment getItem(int position) {
        if(position < mFragments.size()){
            return mFragments.get(position);
        }
        Class<? extends Fragment> targetClass = getClazz(position);
        try {
            Bundle bundle = new Bundle();
            bundle.putString(BundleKey.DATA, String.valueOf(position));
            Fragment fragment = targetClass.newInstance();
            fragment.setArguments(bundle);
            mFragments.add(fragment);
            return fragment;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Class<? extends BaseFragment> getClazz(int position){
        if(mCount == 1 || position >= getCount() - 1){
            return WelcomeFragment.class;
        }
        return GuideFragment.class;
    }

    @Override
    public int getCount() {
        return mCount;
    }
}
