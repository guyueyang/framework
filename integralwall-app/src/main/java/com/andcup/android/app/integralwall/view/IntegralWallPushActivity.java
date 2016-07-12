package com.andcup.android.app.integralwall.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.event.LoginEvent;
import com.andcup.android.app.integralwall.event.OpenTaskDetailEvent;
import com.andcup.android.app.integralwall.view.base.BaseActionBar;
import com.andcup.android.app.integralwall.view.base.BaseColorActivity;
import com.andcup.android.app.integralwall.view.family.FamilyFragment;
import com.andcup.android.app.integralwall.view.home.sign.CalendarFragment;
import com.andcup.android.app.integralwall.view.msg.MsgDetailFragment;
import com.andcup.android.app.integralwall.view.navigator.NavigatorActivity;
import com.andcup.android.app.integralwall.view.start.login.PushLoginFragment;
import com.andcup.android.app.integralwall.view.task.detail.QuickTaskDetailFragment;
import com.andcup.android.app.integralwall.view.task.my.TodoTaskFragment;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.frame.view.navigator.ActivityNavigator;
import com.andcup.android.frame.view.navigator.FragmentNavigator;
import com.andcup.sdk.push.PushSdk;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/15.
 */
public class IntegralWallPushActivity extends BaseColorActivity implements BaseActionBar {
    @Restore(value = BundleKey.PUSH_TYPE)
    String mPushType;
    @Bind(R.id.include_title_bar)
    View     mTitleBar;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    public static void go(Activity activity, Class<? extends Fragment> clazz, Bundle value){
        ActivityNavigator activityNavigator = new ActivityNavigator(activity);
        if( null == value){
            value = new Bundle();
        }
        value.putSerializable(BundleKey.TARGET_CLAZZ, clazz);
        activityNavigator.to(IntegralWallPushActivity.class).with(value).go();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_container;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTitleBar.setVisibility(View.GONE);
        String uid = UserProvider.getInstance().getUid();
        FragmentNavigator fragmentNavigator = new FragmentNavigator(getSupportFragmentManager());
        if(TextUtils.isEmpty(uid)){
            // 未登陆情况下，提示用户先登录.
            fragmentNavigator.at(R.id.fr_container).to(PushLoginFragment.class).with(getIntent().getExtras()).go();
        }else{
            callPushId();
            go();
        }
    }

    private void go(){
        Class<? extends Fragment> clazz = getClazz();
        if( null != clazz){
            go2(clazz, getIntent().getExtras());
        }
        finish();
    }

    @Subscribe
    public void onLoginEvent(LoginEvent loginEvent){
        callPushId();
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    go();
                }catch (Exception e){

                }
            }
        }, 100);
    }

    public Class<? extends Fragment> getClazz(){
        int pushType = Integer.valueOf(mPushType);
        switch (pushType){
            case PushSdk.MSG_INVITE:
                return FamilyFragment.class;
            case PushSdk.MSG_TASK_DETAIL:
                return QuickTaskDetailFragment.class;
            case PushSdk.MSG_SYSTEM_MSG:
                return MsgDetailFragment.class;
            case PushSdk.MSG_WAIT_FINISH_TASK:
                return TodoTaskFragment.class;
            case PushSdk.MSG_SIGN:
                return CalendarFragment.class;
        }
        return null;
    }

    @OnClick(R.id.iv_back)
    public void onBack(){
        finish();
    }

    public void setTitle(String title){
        mTitleBar.setVisibility(TextUtils.isEmpty(title) ? View.GONE: View.VISIBLE);
        mTvTitle.setText(title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for(int i = 0; null != fragmentList && i< fragmentList.size(); i++){
            fragmentList.get(i).onActivityResult(requestCode, resultCode, data);
        }
    }

    @Subscribe
    public void openTask(OpenTaskDetailEvent taskEvent){
        Integer taskId = taskEvent.getTaskId();
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.TASK_ID, String.valueOf(taskId));
        go2(QuickTaskDetailFragment.class, bundle);
    }

}
