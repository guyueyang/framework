package com.andcup.android.app.integralwall.view;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.*;
import android.widget.LinearLayout;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.view.annotations.Restore;
import com.yl.android.cpa.R;

import butterknife.Bind;

/**
 * Created by Amos on 2015/9/24.
 */
public class WebViewFragment extends BaseFragment{

    @Bind(R.id.wv_page)
    WebView mWvPage;
    @Bind(R.id.ll_loading)
    LinearLayout mLlLoading;

    @Restore(BundleKey.TITLE)
    String mTitle;

    @Restore(BundleKey.URL)
    String mUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_web_view;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        setTitle(mTitle);

        mWvPage.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.indexOf("tel:")<0){
                    mLlLoading.setVisibility(View.VISIBLE);
                    view.loadUrl(url);
                }
                return true;
            }
        });
        WebSettings settings = mWvPage.getSettings();
        settings.setJavaScriptEnabled(true);

        mWvPage.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    mLlLoading.setVisibility(View.GONE);
                }
            }
        });

        mWvPage.loadUrl(mUrl);
        mLlLoading.setVisibility(View.VISIBLE);
        mWvPage.setFocusable(true);
        mWvPage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode ==  KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP){
                    if(mWvPage.canGoBack()){
                        mWvPage.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
