package com.andcup.android.app.integralwall.tools;

import android.app.Activity;
import android.os.Bundle;

import com.andcup.android.app.integralwall.view.IntegralWallActivity;
import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.view.WebViewFragment;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/24.
 */
public class LinkViewHelper {

    public static void open(Activity activity, String url, String title){
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.URL, url);
        bundle.putString(BundleKey.TITLE, title);
        IntegralWallActivity.go(activity, WebViewFragment.class, bundle);
    }
}
