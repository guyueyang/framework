package com.andcup.android.app.integralwall.view.task.biz.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.TextUtils;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobPushApps;
import com.andcup.android.app.integralwall.datalayer.model.TaskBusinessEntity;
import com.andcup.android.app.integralwall.datalayer.tools.TimeUtils;
import com.andcup.android.app.integralwall.tools.Sharef;
import com.andcup.android.app.integralwall.view.task.biz.TaskBusiness;
import com.andcup.android.frame.datalayer.job.JobCaller;
import com.andcup.android.integralwall.commons.tools.AndroidUtils;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/18.
 */
public class TaskServices extends Service {

    private static TaskServices INST;

    private TaskBusinessEntity mEntity;
    private InstallerReceived  mInstallerReceived;
    private JobCaller          mJobCaller;

    public class InstallerReceived extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
                String packageName = intent.getDataString();
                try{
                    packageName = packageName.substring("package:".length());
                    if(mEntity.getApkPackage().equals(packageName)){
                        /**安装完成，删除安装包.*/
                        SnackBar.make(getApplicationContext(), getResources().getString(R.string.delete_after_installed)).show();
                        Sharef.setDownload(true, mEntity.getApkPackage());
                        /**安装完成，自动启动.*/
                        AndroidUtils.runApp(getApplicationContext(), packageName);
                        insert(mEntity);
                        try{
                            /**安装完成，删除安装包.*/
                            SnackBar.make(getApplicationContext(), mEntity.getOptionInfo()).show();
                            new File(mEntity.getAppPath()).delete();
                        }catch (Exception e){

                        }
                        /**通知服务端*/
                        mJobCaller.call(new JobPushApps(), new SimpleCallback<>());
                        /**任务启动后，监听完成.*/
                        mEntity = null;
                    }
                }catch (Exception e){

                }
            }
        }
    }

    public static TaskServices getInstance(){
        return INST;
    }

    public void stopMonitor(){
        mEntity = null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INST = this;
        EventBus.getDefault().register(this);
        mJobCaller = new JobCaller();
        mInstallerReceived = new InstallerReceived();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addDataScheme("package");
        this.registerReceiver(mInstallerReceived, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(mInstallerReceived != null) {
            this.unregisterReceiver(mInstallerReceived);
        }
    }

    @Subscribe
    public void onNewBusiness(TaskBusinessEntity entity){
        if(TextUtils.isEmpty(entity.getTaskId())){
            return;
        }
        mEntity = entity;
        mEntity.setDate(TimeUtils.getCurrentDay());
        if(AndroidUtils.isApkInstalled(this, mEntity.getApkPackage())){
            insert(mEntity);
        }
    }

    private void insert(TaskBusinessEntity entity){
        /**
         * insert to data base.
         * */
        entity.setTimeStart(System.currentTimeMillis());
        TaskBusiness.Action.insert(entity);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void start(Context context){
        Intent intent = new Intent(context, TaskServices.class);
        context.startService(intent);
    }

    public static void stop(Context context){
        Intent intent = new Intent(context, TaskServices.class);
        context.stopService(intent);
    }
}
