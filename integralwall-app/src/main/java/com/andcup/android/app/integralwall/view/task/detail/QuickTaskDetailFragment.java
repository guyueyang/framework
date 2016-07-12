package com.andcup.android.app.integralwall.view.task.detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.exception.BizException;
import com.andcup.android.app.integralwall.datalayer.job.JobGetTaskDetails;
import com.andcup.android.app.integralwall.datalayer.job.JobTaskBusiness;
import com.andcup.android.app.integralwall.datalayer.model.CompleteInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskBusinessEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskDetailEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.event.SnapshotEvent;
import com.andcup.android.app.integralwall.tools.Dialog;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.tools.Sharef;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.task.biz.TaskBusiness;
import com.andcup.android.app.integralwall.view.task.snapshot.SnapshotFragment;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.htmltextview.HtmlTextView;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.andcup.android.integralwall.commons.widget.URIRoundedImageView;
import com.andcup.android.app.integralwall.view.task.biz.TaskBusinessFragment;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/17.
 */
public class QuickTaskDetailFragment extends BaseFragment implements SqlLoader.CallBack<TaskBusinessEntity> {

    @Restore(value = BundleKey.TASK_ID)
    String mTaskId;
    @Bind(R.id.lv_task_option)
    ListView mSrvTaskOption;
    @Bind(R.id.riv_avatar)
    URIRoundedImageView mRvAvatar;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.tv_score)
    TextView mTvScore;
    @Bind(R.id.rv_snapshots)
    RecyclerView mRvSnapshots;
    @Bind(R.id.tv_describe)
    HtmlTextView mTvDescribe;
    @Bind(R.id.sv_container)
    ScrollView mSvContainer;

    TaskOptionAdapter mAdapter;
    SnapshotsAdapter mSnapshotAdapter;
    TaskDetailEntity mTaskDetailEntity;

    int mMonitorLoaderId = genLoaderId();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quick_task_detail;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        setTitle(R.string.task_detail);
        mAdapter = new TaskOptionAdapter(getActivity());
        mSrvTaskOption.setAdapter(mAdapter);

        mSnapshotAdapter = new SnapshotsAdapter(getActivity());
        mRvSnapshots.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRvSnapshots.setAdapter(mSnapshotAdapter);

        load();
        call();
        setupTaskBusiness();
    }

    private void setupTaskBusiness(){
        Bundle bundle = getArguments();
        bundle.putSerializable(BundleKey.CLAZZ, TaskDetailEntity.class);
        go(TaskBusinessFragment.class, R.id.fr_business, bundle);
    }

    public void call() {
        call(new JobGetTaskDetails(mTaskId), new SimpleCallback<BaseEntity<TaskDetailEntity>>() {
            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new BizException(e.getMessage()));
            }
        });
    }

    @Subscribe
    public void onSnapshotEvent(SnapshotEvent event){
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.TASK_DETAIL, mTaskDetailEntity);
        Dialog.SNAPSHOT.build(SnapshotFragment.class, bundle).show(getChildFragmentManager());
    }

    @Override
    public void onResume() {
        super.onResume();
        endTaskBusiness();
    }

    private void load() {
        loader(TaskDetailEntity.class, Where.taskDetail(mTaskId), new SqlLoader.CallBack<TaskDetailEntity>() {
            @Override
            public void onUpdate(TaskDetailEntity taskDetailEntity) {
                if (null != taskDetailEntity) {
                    mSvContainer.smoothScrollTo(0, 0);
                    mTvTitle.setText(taskDetailEntity.getTaskName());
                    mTvContent.setText(taskDetailEntity.getAppSize());
                    mTvScore.setText(FormatString.newScore(taskDetailEntity.getTaskTotalScore()));
                    mAdapter.notifyDataSetChanged(Integer.parseInt(taskDetailEntity.getTaskOptionId()), taskDetailEntity);
                    mTvDescribe.setHtmlText(taskDetailEntity.getActivityIntro());
                    mRvAvatar.setImageURI(Uri.parse(taskDetailEntity.getImageUrl()));

                    mSnapshotAdapter.notifyDataSetChanged(taskDetailEntity.getSnapshots());
                    int size = (null == taskDetailEntity.getSnapshots())? 0:taskDetailEntity.getSnapshots().size();
                    mRvSnapshots.setVisibility((size <= 0 || !Sharef.isFlowEnabled(true)) ? View.GONE:View.VISIBLE);
                    mTaskDetailEntity = taskDetailEntity;
                    endTaskBusiness();
                }
            }
        });
    }

    private void monitor(){
        loader(mMonitorLoaderId, TaskBusinessEntity.class, Where.taskBusiness(mTaskId, mTaskDetailEntity.getTaskOptionId()), this);
    }

    private void endTaskBusiness(){
        if( null != mTaskDetailEntity){
            TaskBusinessEntity entity = TaskBusiness.Action.query(mTaskId, mTaskDetailEntity.getTaskOptionId());
            if(null != entity && entity.getTimeEnd() <= entity.getTimeStart()){
                entity.setTimeEnd(System.currentTimeMillis());
                TaskBusiness.Action.update(entity);
                monitor();
            }
        }
    }

    @Override
    public void onUpdate(TaskBusinessEntity taskBusinessEntity) {
        if(null == taskBusinessEntity || taskBusinessEntity.getTimeEnd() <= taskBusinessEntity.getTimeStart()){
            // time not valid.
            return;
        }
        if(!taskBusinessEntity.isFinish()){
            Spanned content = Html.fromHtml(getString(R.string.task_error, taskBusinessEntity.getLimitRunTime()/1000));
            TaskBusiness.Action.delete(taskBusinessEntity);
            SnackBar.make(getActivity(), content).show();
            return;
        }
        callBusiness(taskBusinessEntity);
    }

    private void callBusiness(TaskBusinessEntity taskBusinessEntity){
        if(taskBusinessEntity.isNeedSnapshot()){
            onSnapshotEvent(null);
            mAdapter.notifyDataSetChanged();
            return;
        }
        showLoading();
        call(new JobTaskBusiness(taskBusinessEntity), new SimpleCallback<BaseEntity<CompleteInfoEntity>>() {
            @Override
            public void onError(Throwable e) {
                hideLoading();
                SnackBar.make(getActivity(), e.getMessage()).show();
            }

            @Override
            public void onSuccess(BaseEntity<CompleteInfoEntity> completeInfoEntityBaseEntity) {
                if (null == completeInfoEntityBaseEntity || completeInfoEntityBaseEntity.body() == null) {
                    SnackBar.make(getActivity(), getString(R.string.commit_failed)).show();
                    return;
                }
                TaskBusiness.Action.delete(taskBusinessEntity);
                call();
                SnackBar.make(getActivity(), getString(R.string.task_complete_info, taskBusinessEntity.getScore())).show();
                hideLoading();
            }
        });
    }
}
