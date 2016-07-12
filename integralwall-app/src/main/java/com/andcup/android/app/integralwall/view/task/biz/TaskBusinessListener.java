package com.andcup.android.app.integralwall.view.task.biz;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.andcup.android.app.integralwall.IntegralWallApplication;
import com.andcup.android.app.integralwall.datalayer.model.TaskDetailEntity;
import com.andcup.android.app.integralwall.tools.Sharef;
import com.andcup.android.app.integralwall.view.task.biz.load.TaskLoader;
import com.andcup.android.integralwall.commons.loading.LoadingIndicatorView;
import com.andcup.android.integralwall.commons.tools.AndroidUtils;
import com.andcup.lib.download.DownloadListener;
import com.andcup.lib.download.ErrorType;
import com.andcup.lib.download.data.model.DownloadStatus;
import com.yl.android.cpa.R;

import java.lang.ref.SoftReference;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/18.
 */
public class TaskBusinessListener implements DownloadListener {

    private ProgressBar mProgress;
    private Button mBtnText;
    private LoadingIndicatorView mLoading;
    private SoftReference<TaskLoader> mLoader;
    private TaskBusiness.OnLoadListener mListener;

    public TaskBusinessListener(TaskLoader loader, TaskBusiness.OnLoadListener listener, ProgressBar progressBar, Button text, LoadingIndicatorView loadingIndicatorView) {
        mProgress = progressBar;
        mBtnText = text;
        mLoading = loadingIndicatorView;
        mLoader = new SoftReference<>(loader);
        mListener = listener;
    }

    private boolean isMine(long taskId) {
        try {
            return (mLoader.get().getTaskId() == taskId);
        } catch (Exception e) {

        }
        return false;
    }

    public void onError() {
        mLoading.setVisibility(View.GONE);
        mBtnText.setVisibility(View.VISIBLE);
    }

    public void update(TaskDetailEntity detailEntity) {
        if (null == detailEntity) {
            mLoading.setVisibility(View.VISIBLE);
            mBtnText.setVisibility(View.GONE);
            return;
        }
        mBtnText.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
        if(AndroidUtils.isApkInstalled(mLoading.getContext(), detailEntity.getAppUri())
                &&(Sharef.isDownload(detailEntity.getAppUri())
                ||!detailEntity.isFirstDayTask())){
            mBtnText.setText(getString(R.string.task_download_finish));
        }else if (!mLoader.get().isStarted()) {
            mBtnText.setText(getString(R.string.task_download_start));
        } else if (mLoader.get().isCompleted(false)) {
            mBtnText.setText(getString(R.string.task_download_finish));
        } else if (mLoader.get().isPause()) {
            mBtnText.setText(getString(R.string.task_download_pause, mLoader.get().getProgress()));
        } else {
            mBtnText.setText(getString(R.string.task_download_progress, mLoader.get().getProgress()));
        }
        mProgress.setProgress(mLoader.get().getProgress());
    }

    private String getString(int resId, int progress) {
        return IntegralWallApplication.getInstance().getString(resId, progress) + "%";
    }

    public final String getString(int resId) {
        return IntegralWallApplication.getInstance().getString(resId);
    }

    @Override
    public void onAdd(long taskId) {
        if (isMine(taskId)) {
            mBtnText.setText(getString(R.string.task_download_progress, mLoader.get().getProgress()));
            mProgress.setProgress(mLoader.get().getProgress());
        }
    }

    @Override
    public void onStart(long taskId, int i) {
        if (isMine(taskId)) {
            mBtnText.setText(getString(R.string.task_download_progress, i));
            mProgress.setProgress(i);
        }
    }

    @Override
    public void onPrepared(long taskId) {
        if (isMine(taskId)) {
            mBtnText.setText(getString(R.string.task_download_progress, mLoader.get().getProgress()));
            mProgress.setProgress(mLoader.get().getProgress());
        }
    }

    @Override
    public void onPause(long taskId, DownloadStatus downloadStatus) {
        if (isMine(taskId)) {
            mBtnText.setText(getString(R.string.task_download_pause, mLoader.get().getProgress()));
            mProgress.setProgress(mLoader.get().getProgress());
        }
    }

    @Override
    public void onProgress(long taskId, int i) {
        if (isMine(taskId)) {
            mBtnText.setText(getString(R.string.task_download_progress, i));
            mProgress.setProgress(i);
        }
    }

    @Override
    public void onComplete(long taskId) {
        if (isMine(taskId)) {
            mBtnText.setText(getString(R.string.task_download_finish));
            mProgress.setProgress(mLoader.get().getProgress());
            if (null != mListener) {
                mListener.onComplete(mLoader.get().getLocalPath());
            }
        }
    }

    @Override
    public void onError(long taskId, ErrorType errorType) {
        if (isMine(taskId)) {
            mBtnText.setText(getString(R.string.task_download_start));
            mProgress.setProgress(mLoader.get().getProgress());
        }
    }

    @Override
    public void onWait(long taskId) {
        if (isMine(taskId)) {
        }
    }

    @Override
    public void onSpeed(long taskId, long l1) {
        if (isMine(taskId)) {

        }
    }

    @Override
    public void onDeleted(long taskId) {
        if (isMine(taskId)) {

        }
    }
}
