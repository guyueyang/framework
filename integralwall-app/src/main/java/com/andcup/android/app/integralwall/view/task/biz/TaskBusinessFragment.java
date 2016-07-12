package com.andcup.android.app.integralwall.view.task.biz;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.exception.BizException;
import com.andcup.android.app.integralwall.datalayer.model.TaskDetailEntity;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.task.biz.load.TaskLoader;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.loading.LoadingIndicatorView;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/18.
 */
public class TaskBusinessFragment extends BaseFragment implements TaskBusiness.OnLoadListener {

    @Restore(value = BundleKey.TASK_ID)
    String           mTaskId;
    TaskDetailEntity mTask;
    TaskLoader       mTaskLoader;
    @Bind(R.id.pb_task)
    ProgressBar      mPbTask;
    @Bind(R.id.btn_download)
    Button           mBtnDownload;
    @Bind(R.id.liv_loading)
    LoadingIndicatorView mLoading;

    TaskBusinessListener mBusinessListener;
    TaskBusiness mBusiness;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_download;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTaskLoader = new TaskLoader();
        mBusinessListener = new TaskBusinessListener(mTaskLoader, this, mPbTask, mBtnDownload, mLoading);
        mTaskLoader.addListener(mBusinessListener);
        mBusiness = new TaskBusiness(getActivity(), mTaskLoader);
        load();
    }

    @Override
    public void onDestroyView() {
        mTaskLoader.removeListener(mBusinessListener);
        super.onDestroyView();
    }

    @Subscribe
    public void onError(BizException bizException){
        mBusinessListener.onError();
    }

    private void load() {
        loader(TaskDetailEntity.class, Where.taskDetail(mTaskId), new SqlLoader.CallBack<TaskDetailEntity>() {
            @Override
            public void onUpdate(TaskDetailEntity taskEntity) {
                if( null != taskEntity){
                    mTask = taskEntity;
                    mTaskLoader.setFilePath(taskEntity.getAppPath());
                    mBusiness.setTaskDetailEntity(taskEntity);
                }
                mBusinessListener.update(taskEntity);
            }
        });
    }

    @OnClick(R.id.btn_download)
    public void onDownloadClick() {
        if( null == mTask){
            return;
        }
        mBusiness.start(mTaskLoader.getLocalPath());
    }

    @Override
    public void onComplete(String filepath) {
        // start business
        mBusiness.start(filepath);
    }
}
