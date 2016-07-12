package com.andcup.android.app.integralwall.view.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.andcup.android.app.integralwall.view.IntegralWallActivity;
import com.andcup.android.frame.view.RxDialogFragment;
import com.andcup.android.frame.view.navigator.ActivityNavigator;
import com.andcup.android.frame.view.navigator.FragmentNavigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/9.
 */
public abstract class BaseDialogFragment extends RxDialogFragment{

    protected void go(Class<? extends Activity> target){
        ActivityNavigator navigator = new ActivityNavigator(getActivity());
        navigator.to(target).go().finish();
    }

    protected void go(Class<? extends Fragment> target, int containerId, Bundle bundle){
        FragmentNavigator navigator = new FragmentNavigator(getChildFragmentManager());
        navigator.at(containerId).to(target).with(bundle).go();
    }

    protected void go(Class<? extends Fragment> target, int containerId){
        FragmentNavigator navigator = new FragmentNavigator(getChildFragmentManager());
        navigator.at(containerId).to(target).go();
    }

    protected void finish(Class<? extends Fragment> target){
        FragmentNavigator navigator = new FragmentNavigator(getChildFragmentManager());
        navigator.to(target).finish();
    }

    protected void go2(Class<? extends Fragment> target, Bundle value){
        IntegralWallActivity.go(getActivity(), target, value);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Object object){
    }

    protected Point getWindowSize(){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return new Point(dm.widthPixels, dm.heightPixels);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        List<Fragment> fragmentList = getChildFragmentManager().getFragments();
//        for(int i = 0; null != fragmentList && i< fragmentList.size(); i++){
//            fragmentList.get(i).onActivityResult(requestCode, resultCode, data);
//        }
    }
}
