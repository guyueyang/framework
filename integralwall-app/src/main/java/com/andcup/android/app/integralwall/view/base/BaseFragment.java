package com.andcup.android.app.integralwall.view.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.app.integralwall.view.IntegralWallActivity;
import com.andcup.android.app.integralwall.view.LoadingDialogFragment;
import com.andcup.android.frame.view.RxFragment;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.andcup.android.frame.view.navigator.ActivityNavigator;
import com.andcup.android.frame.view.navigator.DialogFragmentNavigator;
import com.andcup.android.frame.view.navigator.FragmentNavigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/9.
 */
public abstract class BaseFragment extends RxFragment implements ColorBar{

    ColorBar mColorBar;

    protected void go(Class<? extends Activity> target){
        ActivityNavigator navigator = new ActivityNavigator(getActivity());
        navigator.to(target).go().finish();
    }

    protected void go3(Class<? extends Activity> target, Bundle bundle){
        ActivityNavigator navigator = new ActivityNavigator(getActivity());
        navigator.to(target).with(bundle).go().finish();
    }

    protected void go(Class<? extends Fragment> target, int containerId){
        FragmentNavigator navigator = new FragmentNavigator(getChildFragmentManager());
        navigator.at(containerId).to(target).go();
    }

    protected void go(Class<? extends Fragment> target, int containerId, Bundle bundle){
        FragmentNavigator navigator = new FragmentNavigator(getChildFragmentManager());
        navigator.at(containerId).with(bundle).to(target).go();
    }

    protected void finish(Class<? extends Fragment> target){
        FragmentNavigator navigator = new FragmentNavigator(getChildFragmentManager());
        navigator.to(target).finish();
    }

    protected void finishDialog(Class<? extends Fragment> target){
        DialogFragmentNavigator navigator = new DialogFragmentNavigator(getFragmentManager());
        navigator.to(target).finish();
    }

    protected void go2(Class<? extends Fragment> target, Bundle value){
        IntegralWallActivity.go(getActivity(), target, value);
    }

    protected void showLoading(){
        DialogFragmentNavigator navigator = new DialogFragmentNavigator(getChildFragmentManager());
        navigator.to(LoadingDialogFragment.class).go();
    }

    protected void hideLoading(){
        DialogFragmentNavigator navigator = new DialogFragmentNavigator(getChildFragmentManager());
        navigator.to(LoadingDialogFragment.class).finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        SdkManager.INST.mUmeng.onPageStart(this.getClass().toString());
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        SdkManager.INST.mUmeng.onPageEnd(this.getClass().toString());
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(Object object){
    }

    protected void setTitle(String title){
        if(getActivity() instanceof BaseActionBar){
            ((BaseActionBar)getActivity()).setTitle(title);
        }
    }

    protected void setTitle(int title){
        if(getActivity() instanceof BaseActionBar){
            ((BaseActionBar)getActivity()).setTitle(getString(title));
        }
    }

    protected Point getWindowSize(){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return new Point(dm.widthPixels, dm.heightPixels);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ColorBar){
            mColorBar = (ColorBar) activity;
        }
    }

    @Override
    public void setBarColor(int color) {
        if( null != mColorBar){
            mColorBar.setBarColor(color);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            List<Fragment> fragmentList = getChildFragmentManager().getFragments();
            for(int i = 0; null != fragmentList && i< fragmentList.size(); i++){
                fragmentList.get(i).onActivityResult(requestCode, resultCode, data);
            }
        }catch (Exception e){

        }
    }
}
