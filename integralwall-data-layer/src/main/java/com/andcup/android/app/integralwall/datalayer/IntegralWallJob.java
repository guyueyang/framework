package com.andcup.android.app.integralwall.datalayer;


import com.andcup.android.app.integralwall.datalayer.config.Logs;
import com.andcup.android.app.integralwall.datalayer.tools.DeviceUtils;
import com.andcup.android.app.integralwall.datalayer.tools.MD5;
import com.andcup.android.frame.datalayer.job.Job;
import com.andcup.android.frame.datalayer.job.JobEntity;
import com.andcup.android.frame.datalayer.exception.JobException;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/14.
 */
public abstract class IntegralWallJob<T extends JobEntity> implements Job<T>{

    //提现之前必须先做任务.
    public static final int DO_TASK_BEFORE_CASH_CHECK = -9;
    //积分不足
    public static final int LACK_INTEGRAL=-6;

    public static final int PHONE_FARE_DO_TASK_BEFORE_CASH_CHECK=-6;
    //在异地登录
    public static final int LOGIN_AT_ANOTHER = -105;
    //账号被禁用
    public static final int ACCOUNT_DISABLED = -107;
    // 该设备已绑定过手机号
    public static final int EQUIPMENT_HAS_BEEN_BINDING_NUMBER=-111;

    @Override
    public void finish(T t) throws JobException {

    }

    protected static IntegralWallApis apis(){
        return IntegralWallDataLayer.getInstance().getRepository().from();
    }

    protected static String getUniqueId(){
        return DeviceUtils.getUniqueId(IntegralWallDataLayer.getInstance().getContext());
    }

    protected static long  getTimestamp(){
        return System.currentTimeMillis();
    }

    protected static String getKey(){
        return "5bc9c259616b8828814ae23ef6f17263";
    }

    protected static String md5(String value){
        return MD5.toMd5(value);
    }

    protected void log(String tag,  String message){
        String applist = tag + "\n" + message + "\n========================== \n";
        Logs.LOG1.log(applist);
    }
}
