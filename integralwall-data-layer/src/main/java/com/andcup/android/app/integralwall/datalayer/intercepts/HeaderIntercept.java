package com.andcup.android.app.integralwall.datalayer.intercepts;

import android.os.Build;
import android.text.TextUtils;

import com.andcup.android.app.integralwall.datalayer.IntegralWallDataLayer;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.config.ChannelConfigure;
import com.andcup.android.app.integralwall.datalayer.tools.DeviceUtils;
import com.andcup.android.app.integralwall.datalayer.tools.MD5;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/4/19.
 */
public class HeaderIntercept implements Interceptor{

    public static String getUuid() {
        String oldUid = DeviceUtils.getUniqueId();
        //imei
        String imei = DeviceUtils.getIMEI(IntegralWallDataLayer.getInstance().getContext());
        if(TextUtils.isEmpty(imei)){
            imei = "imei is null";
        }
        return MD5.toMd5(oldUid + imei);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        // android type.
        request.addHeader("device_type", "1");
        // old uid.
        String oldUid = DeviceUtils.getUniqueId();
        request.addHeader("uuid3", oldUid);

        // mac
        String mac = DeviceUtils.getMacAddress(IntegralWallDataLayer.getInstance().getContext());
        try {
            request.addHeader("mac", mac);
        } catch (Exception e) {
            mac = "mac is null";
            request.addHeader("mac", mac);
        }
        //imei
        String imei = DeviceUtils.getIMEI(IntegralWallDataLayer.getInstance().getContext());

        try{
            request.addHeader("imei", imei);
        }catch (Exception e){
            imei = "imei is null";
            request.addHeader("imei", imei);
        }
        //imsi
        String imsi = DeviceUtils.getIMSI(IntegralWallDataLayer.getInstance().getContext());

        try{
            request.addHeader("imsi", imsi);
        }catch (Exception e){
            imsi = "imsi is null";
            request.addHeader("imsi", imsi);
        }
        // uuid2
        String newUid = MD5.toMd5(oldUid + imei + imei + imsi);
        request.addHeader("uuid2", newUid);
        // uuid
        String newUid2 = MD5.toMd5(oldUid + imei);
        request.addHeader("uuid", newUid2);

        request.addHeader("channel", ChannelConfigure.getInstance().getChannel());
        request.addHeader("version", DeviceUtils.getVerName(IntegralWallDataLayer.getInstance().getContext()));
        request.addHeader("build_version", String.valueOf(DeviceUtils.getVerCode(IntegralWallDataLayer.getInstance().getContext())));
        request.addHeader("os_version", Build.VERSION.RELEASE);
        request.addHeader("device_model", Build.MODEL);
        if (!TextUtils.isEmpty(UserProvider.getInstance().getAccessToken())) {
            request.addHeader("access_token", UserProvider.getInstance().getAccessToken());
        }
        return chain.proceed(request.build());
    }
}
