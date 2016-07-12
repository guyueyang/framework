package com.andcup.android.app.integralwall.view.navigator;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.IntegralWallDataLayer;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.config.ChannelConfigure;
import com.andcup.android.app.integralwall.datalayer.job.JobGetUserInfo;
import com.andcup.android.app.integralwall.datalayer.job.JobInviteCode;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.event.CheckNavigatorItemEvent;
import com.andcup.android.app.integralwall.event.OpenFirstLandingTaskEvent;
import com.andcup.android.app.integralwall.event.OpenTaskDetailEvent;
import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.app.integralwall.tools.AppHelper;
import com.andcup.android.app.integralwall.tools.Dialog;
import com.andcup.android.app.integralwall.tools.Sharef;
import com.andcup.android.app.integralwall.view.base.BaseNetworkActivity;
import com.andcup.android.app.integralwall.view.dialog.InviteFragment;
import com.andcup.android.app.integralwall.view.guide.TaskGuideFragment;
import com.andcup.android.app.integralwall.view.task.detail.QuickTaskDetailFragment;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.widget.ScrolledViewPager;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.andcup.android.update.model.UpdateEntity;
import com.andcup.sdk.push.PushEvent;
import com.andcup.sdk.push.PushSdk;
import com.andcup.widget.AutoRelativeLayout;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/9.
 */
public class NavigatorActivity extends BaseNetworkActivity implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.rg_navigator)
    RadioGroup mRgNavigator;
    @Bind(R.id.vp_navigator)
    ScrolledViewPager   mVpNavigator;

    @Bind(R.id.rl_root)
    AutoRelativeLayout mRlRoot;

    @Restore(BundleKey.CHECK_ITEM)
    String mCheckItem;
    NavigatorAdapter    mAdapter;

    boolean mDoubleClick = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_intrgral_wall;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mAdapter = new NavigatorAdapter(this, getSupportFragmentManager());
        mVpNavigator.setAdapter(mAdapter);
        mVpNavigator.setScrollable(false);
        mVpNavigator.setOffscreenPageLimit(5);
        //mVpNavigator.setPageTransformer(true, new StackTransformer());
        // config navigator bar.
        LayoutInflater inflater = LayoutInflater.from(this);
        for(int i = 0; i < NavigatorAdapter.NAVIGATORS.length; i++ ){
            inflater.inflate(R.layout.group_item_home, mRgNavigator);
            RadioButton rb = (RadioButton) mRgNavigator.getChildAt(i);
            // set view id.
            rb.setId(NavigatorAdapter.NAVIGATORS[i].getTitle());
            // set view text.
            rb.setText(getResources().getString(NavigatorAdapter.NAVIGATORS[i].getTitle()));
            // set view drawable.
            Drawable drawable = getResources().getDrawable(NavigatorAdapter.NAVIGATORS[i].getIcon());
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            rb.setCompoundDrawables(null, drawable, null, null);
        }

        mRgNavigator.setOnCheckedChangeListener(this);
        // check home item.
        mRgNavigator.check(NavigatorAdapter.NAVIGATORS[0].getTitle());
        // invite user.
        call();
        //
        PushSdk.getInstance().setAlias(UserProvider.getInstance().getUid());
        // auto check default item.
        checkItem();
    }

    private void checkItem(){
        if(!TextUtils.isEmpty(mCheckItem)){
            getView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try{
                        int index = Integer.valueOf(mCheckItem);
                        mRgNavigator.check(NavigatorAdapter.NAVIGATORS[index].getTitle());
                        callPushId();
                    }catch (Exception e){

                    }
                }
            }, 20);
        }
    }

    private void call(){
        ChannelConfigure channelConfigure = ChannelConfigure.getInstance();
        String inviteCode = channelConfigure.getInviteCode();
        if(!TextUtils.isEmpty(inviteCode) && !inviteCode.equals("0")){
            try{
                UserProvider.getInstance().getUserInfo().setInviteUid(inviteCode);
            }catch (Exception e){

            }
            call(new JobInviteCode(inviteCode), new SimpleCallback<BaseEntity>());
        }else{
            String inviteId = UserProvider.getInstance().getInviteUid();
            if(inviteCode.equals("0") && (TextUtils.isEmpty(inviteId) || inviteId.equals("0"))){
                if(!Sharef.hasInviteNotify()){
                    Dialog.INVITE_CODE.build(InviteFragment.class, null).show(getSupportFragmentManager());
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserInfo();
    }

    private void updateUserInfo(){
        call(new JobGetUserInfo(), new SimpleCallback<BaseEntity<UserInfoEntity>>());
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int viewId) {
        for( int j = 0; j< NavigatorAdapter.NAVIGATORS.length; j++){
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(viewId);
            // find checked item.
            if(NavigatorAdapter.NAVIGATORS[j].getTitle() == viewId && radioButton.isChecked()){
                // goto checked item.
                mVpNavigator.setCurrentItem(j, false);
                // 更新用户信息.
                updateUserInfo();
                //
                //EventBus.getDefault().post(NavigatorAdapter.NAVIGATORS[j]);
                // title bar color changed.
                if(j == 0 || j == 3){
                    setBarColor(R.color.colorActionBar2);
                }else{
                    setBarColor(R.color.colorActionBar);
                }
            }
        }
    }

    @Subscribe
    public void onCheckedItemEvent(CheckNavigatorItemEvent event){
        if(event.getItem() < NavigatorAdapter.NAVIGATORS.length && event.getItem() >= 0){
            mRgNavigator.check(NavigatorAdapter.NAVIGATORS[event.getItem()].getTitle());
        }
    }

    @Subscribe
    public void onPushEvent(PushEvent pushEvent){
        if(pushEvent.getItem() < NavigatorAdapter.NAVIGATORS.length && pushEvent.getItem() >= 0){
            mRgNavigator.check(NavigatorAdapter.NAVIGATORS[pushEvent.getItem()].getTitle());
            callPushId();
        }
    }

    @Subscribe
    public void openTask(OpenTaskDetailEvent taskEvent){
        Integer taskId = taskEvent.getTaskId();
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.TASK_ID, String.valueOf(taskId));
        go2(QuickTaskDetailFragment.class, bundle);
    }

    @Subscribe
    public void openFirstLandingTask(OpenFirstLandingTaskEvent firstLandingTaskEvent){
        go2(TaskGuideFragment.class,null);
    }

    @Override
    public void onBackPressed() {
        if(isMonkey()){
            return;
        }
        if(!mDoubleClick){
            mDoubleClick = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDoubleClick = false;
                }
            }, 3000);
            SnackBar.make(this, getResources().getString(R.string.exit_double_back)).show();
            return;
        }
        AppHelper.exit();
    }
}
