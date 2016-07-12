package com.andcup.android.app.integralwall.tools;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.view.share.ShareContent;
import com.andcup.android.app.integralwall.view.share.ShareFragment;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/24.
 */
public class ShareHelper {

    public static void share(FragmentManager fm, ShareContent shareContent){
        Bundle bundle = new Bundle();
        if( null != shareContent){
            bundle.putSerializable(BundleKey.SHARE_CONTENT, shareContent);
        }
        Dialog.SHARE.build(ShareFragment.class, bundle).show(fm);
    }
}
