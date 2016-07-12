package com.andcup.android.app.integralwall;

import android.app.Activity;
import android.content.Intent;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import com.andcup.android.app.integralwall.datalayer.IntegralWallDataLayer;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.config.ChannelConfigure;
import com.andcup.android.app.integralwall.datalayer.config.ConfigureProvider;
import com.andcup.android.app.integralwall.datalayer.config.Logs;
import com.andcup.android.app.integralwall.datalayer.tools.IOUtils;
import com.andcup.android.app.integralwall.event.ExitEvent;
import com.andcup.android.app.integralwall.event.LogoutEvent;
import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.app.integralwall.third.sdk.UMeng;
import com.andcup.android.app.integralwall.third.sdk.base.ThirdSdk;
import com.andcup.android.app.integralwall.view.start.WelcomeActivity;
import com.andcup.android.app.integralwall.view.task.biz.load.TaskRepository;
import com.andcup.android.app.integralwall.view.task.biz.services.TaskServices;
import com.andcup.android.database.activeandroid.ActiveAndroid;
import com.andcup.android.frame.AndcupApplication;
import com.andcup.android.frame.datalayer.provider.ActiveAndroidProvider;
import com.andcup.android.frame.datalayer.task.RxAsyncTask;
import com.andcup.android.integralwall.commons.tools.AndroidUtils;
import com.andcup.android.update.Update;
import com.andcup.android.update.model.UpdateEntity;
import com.andcup.lib.download.DownloadManager;
import com.andcup.sdk.push.PushSdk;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.OauthHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/3.
 */

public class IntegralWallApplication extends AndcupApplication {

    public static IntegralWallApplication INST;

    private List<SoftReference<Activity>> mActivityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        INST = this;
        //init data layer.
        IntegralWallDataLayer.newInstance(INST);
        //
        initDownloadModule();
        //init user provider.
        UserProvider.init(INST);
        // configure module.
        ConfigureProvider.init(this);
        //
        TaskServices.start(this);
        IntegralWallUpdate.init(this);
        //monitor logout and so on.
        EventBus.getDefault().register(this);
        //clear logs.
        Logs.LOG1.clear();
        Logs.LOG2.clear();
        //初始化SDK.
        SdkManager.initPlatform(IntegralWallApplication.INST,
                ChannelConfigure.getInstance().getChannel(),
                IntegralWallDataLayer
                        .getInstance()
                        .getPlatformConfigure()
                        .isDebug());
        IntegralWallUpdate.getInstance().checkUpdate();

        // init push sdk.
        PushSdk.init(this, ChannelConfigure.getInstance().getChannel());
        // set alias.
        PushSdk.getInstance().setAlias(UserProvider.getInstance().getUid());
    }

    private void initDownloadModule(){
        //init download module.
        try{
            DownloadManager.init(this, AndroidUtils.getDiskCacheDir(this));
            DownloadManager.getInstance().setDownloadOnlyWifi(false);
            DownloadManager.getInstance().setDefaultRepositoryHandler(new TaskRepository());
        }catch (Exception e){

        }
    }

    public void addActivity(Activity activity){
        mActivityList.add(new SoftReference<Activity>(activity));
    }

    private void finish(){
        // finish all activity
        for(int i = 0; i< mActivityList.size(); i++){
            if(mActivityList.get(i).get() != null){
                mActivityList.get(i).get().finish();
            }
        }
        mActivityList.clear();
        // start login activity.
    }

    @Subscribe
    public void logout(LogoutEvent logoutEvent) {
        finish();
        //PushSdk.getInstance().dispose();
        // clear last login user.
        UserProvider.getInstance().clearCache();
        OauthHelper.remove(this, SHARE_MEDIA.SINA);
        OauthHelper.remove(this, SHARE_MEDIA.WEIXIN);
        startWelcome();
    }

    @Subscribe
    public void exit(ExitEvent exitEvent){
        finish();
        DownloadManager.getInstance().finish();
        TaskServices.stop(this);
        ActiveAndroid.dispose();
        INST = null;
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    private void startWelcome(){
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
