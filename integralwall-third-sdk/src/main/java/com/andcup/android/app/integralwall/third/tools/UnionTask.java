package com.andcup.android.app.integralwall.third.tools;

import android.app.Activity;
import android.content.Context;
import android.media.audiofx.Equalizer;

import com.andcup.android.app.integralwall.third.SdkManager;
import com.newqm.pointwall.QEarnNotifier;

/**
 * Created by Administrator on 2016/3/18.
 */
public class UnionTask {


    final String UnionTask_Qumi="1";
    final String UnionTask_BeiDuo="2";
    final String UnionTask_Domob="3";
    final String UnionTask_DianLe="4";
    final String UnionTask_DianRu="5";

    Context mContext;
    Activity mActivity;
    QEarnNotifier mQEarnNotifier;
    String mUnionTaskId;
    public  UnionTask(Context context,Activity activity,QEarnNotifier qEarnNotifier,String unionTaskId){
        mContext=context;
        mActivity=activity;
        mQEarnNotifier=qEarnNotifier;
        mUnionTaskId=unionTaskId;
        openUnionTask(mUnionTaskId);
    }

    public void openUnionTask(String unionTaskId){
        if(unionTaskId.equals(UnionTask_Qumi)){
            try {
                SdkManager.INST.mQumi.configSdk(mContext);
                SdkManager.INST.mQumi.showOffers(mQEarnNotifier);
            }catch (Exception e){
            }
        }else if(unionTaskId.equals(UnionTask_BeiDuo)){
            SdkManager.INST.mBeiDuo.showOffers(mContext);
        }else if(unionTaskId.equals(UnionTask_Domob)){
            SdkManager.INST.mDianLe.initSdk(mActivity);
            SdkManager.INST.mDianLe.showOffers(mActivity);
        }else if(unionTaskId.equals(UnionTask_DianLe)){
            SdkManager.INST.mDomob.showOffers(mContext);
        }else if(unionTaskId.equals(UnionTask_DianRu)){
            SdkManager.INST.mDianRu.configSdk(mContext);
            SdkManager.INST.mDianRu.showOffers(mContext);
        }
    }

}
