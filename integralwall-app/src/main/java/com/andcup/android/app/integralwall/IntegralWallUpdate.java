package com.andcup.android.app.integralwall;

import android.content.Context;

import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.umeng.onlineconfig.UmengOnlineConfigureListener;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/28.
 */
public class IntegralWallUpdate implements UmengUpdateListener, UmengOnlineConfigureListener {

    public static IntegralWallUpdate INST;
    public static boolean sHasNotify = false;

    Context mContext;
    boolean mSlideUpdate = true;
    CheckUpdate  mUpdateCheck = new CheckUpdate();

    private IntegralWallUpdate(Context context){
        mContext = context;
    }

    public static IntegralWallUpdate init(Context context){
        if(null == INST){
            INST = new IntegralWallUpdate(context);
        }
        return INST;
    }

    public static IntegralWallUpdate getInstance(){
        return INST;
    }

    private UmengUpdateListener mUmengUpdateListener;

    public void checkUpdate( ){
        // not notify.
        mSlideUpdate = true;
        //
        SdkManager.INST.mUmeng.setUpdateListener(this);
        // set configure changed listener.
        SdkManager.INST.mUmeng.setOnlineConfigListener(this);
        //update config.
        SdkManager.INST.mUmeng.updateOnlineConfig(mContext);
        //
        mUpdateCheck.startCheck(8000);
    }

    @Deprecated
    public void checkUpdate(UmengUpdateListener umengUpdateListener){
        mSlideUpdate   = false;
        sHasNotify     = false;
        mUmengUpdateListener = umengUpdateListener;
        //check online params.
        SdkManager.INST.mUmeng.updateOnlineConfig(mContext);
        //
        mUpdateCheck.startCheck(5000);
    }

    @Override
    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
        switch (updateStatus) {
            case UpdateStatus.Yes: // has update
                startUpdateActivity(updateInfo);
                break;
            case UpdateStatus.No: // has no update
                if(!mSlideUpdate)
                    SnackBar.make(mContext, mContext.getResources().getString(R.string.update_no)).show();
                break;
            case UpdateStatus.NoneWifi: // none wifi
                if(!mSlideUpdate)
                    SnackBar.make(mContext, mContext.getResources().getString(R.string.update_on_wifi)).show();
                break;
            case UpdateStatus.Timeout: // time out
                if(!mSlideUpdate)
                    SnackBar.make(mContext, mContext.getResources().getString(R.string.update_timeout)).show();
                break;
        }
        if( null != mUmengUpdateListener){
            mUmengUpdateListener.onUpdateReturned(updateStatus, updateInfo);
        }
    }

    private void startUpdateActivity(UpdateResponse updateResponse){
        if(!sHasNotify){
            sHasNotify = true;
            EventBus.getDefault().post(updateResponse);
        }
    }

    @Override
    public void onDataReceived(JSONObject jsonObject) {
        mUpdateCheck.stopCheck();
        SdkManager.INST.mUmeng.checkUpdate(mContext);
    }

    public class CheckUpdate{

        Timer mTimer;

        public void startCheck( long delay ){
            stopCheck();
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try{
                        SdkManager.INST.mUmeng.checkUpdate(mContext);
                    }catch (Exception e){

                    }
                }
            }, delay);
        }

        public void stopCheck(){
            if( null != mTimer){
                mTimer.cancel();
            }
            mTimer = null;
        }
    }
}
