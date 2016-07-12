package com.andcup.android.app.integralwall.view.update;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.app.integralwall.tools.AppHelper;
import com.andcup.android.app.integralwall.view.base.BaseActivity;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.umeng.update.UmengDownloadListener;
import com.umeng.update.UpdateResponse;
import com.yl.android.cpa.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/28.
 */
public class UpdateActivity extends BaseActivity implements UmengDownloadListener {

    @Restore(BundleKey.UPDATE_RESPONSE)
    UpdateResponse mUpdateResponse;
    @Bind(R.id.tv_info)
    TextView mTvInfo;
    @Bind(R.id.pb_task)
    ProgressBar mPbUpdate;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.ib_close)
    ImageButton mIvClose;
    @Bind(R.id.tv_download)
    TextView mTvDownload;
    boolean  mHasDownloaded = false;
    boolean  mDoubleClick = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTvTitle.setText(String.format(getString(R.string.new_version), mUpdateResponse.version));
        mTvInfo.setText(mUpdateResponse.updateLog);
        mHasDownloaded = SdkManager.INST.mUmeng.hasDownload(this, mUpdateResponse);
        if(mHasDownloaded){
            mPbUpdate.setProgress(100);
        }else{
            mPbUpdate.setProgress(0);
        }
        if(isForceClose()){
            mIvClose.setVisibility(View.GONE);
            mTvDownload.setText(getString(R.string.update_force));
        }
    }

    private boolean isForceClose(){
        return SdkManager.INST.mUmeng.isForceUpdate(this);
    }

    @OnClick(R.id.tv_download)
    public void download(){
        if(mHasDownloaded){
            SdkManager.INST.mUmeng.install(this, mUpdateResponse);
        }else{
            mTvDownload.setEnabled(false);
            mTvDownload.setText(getString(R.string.downloading, 0) + "%");
            SdkManager.INST.mUmeng.update(this, mUpdateResponse, this);
        }
    }

    @OnClick(R.id.ib_close)
    public void close(){
        finish();
    }

    @Override
    public void OnDownloadStart() {

    }

    @Override
    public void onBackPressed() {
        if(isForceClose()){
            if(!mDoubleClick){
                mDoubleClick = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDoubleClick = false;
                    }
                }, 3000);
                SnackBar.make(this, getResources().getString(R.string.exit_double_back)).show();
                return;
            }else{
                AppHelper.exit();
            }
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void OnDownloadUpdate(int i) {
        Log.v(UpdateActivity.class.getName(), "download progress = " + i);
        getView().post(new Runnable() {
            @Override
            public void run() {
                mPbUpdate.setProgress(i);
                mTvDownload.setText(getString(R.string.downloading, i) + "%");
            }
        });
    }

    @Override
    public void OnDownloadEnd(int i, String s) {
        mTvDownload.setEnabled(true);
        mPbUpdate.setProgress(100);
        mTvDownload.setEnabled(true);
        mHasDownloaded = true;
        mTvDownload.setText(getString(R.string.update_ok));
    }
}
