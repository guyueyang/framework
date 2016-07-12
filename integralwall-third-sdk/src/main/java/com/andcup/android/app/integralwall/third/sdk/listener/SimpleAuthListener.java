package com.andcup.android.app.integralwall.third.sdk.listener;

import android.os.Bundle;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;

/**
 * Created by Amos on 2015/9/18.
 */
public class SimpleAuthListener implements SocializeListeners.UMAuthListener {
    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SocializeException e, SHARE_MEDIA share_media) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }
}
