package com.andcup.android.app.integralwall.view.task.snapshot;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.GridView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.exception.BizException;
import com.andcup.android.app.integralwall.datalayer.job.JobGetTaskDetails;
import com.andcup.android.app.integralwall.datalayer.job.JobTaskBusiness;
import com.andcup.android.app.integralwall.datalayer.job.JobUploadImages;
import com.andcup.android.app.integralwall.datalayer.model.CompleteInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskBusinessEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskDetailEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.event.AddSnapshotEvent;
import com.andcup.android.app.integralwall.view.base.GateDialogFragment;
import com.andcup.android.app.integralwall.view.task.biz.TaskBusiness;
import com.andcup.android.app.integralwall.view.task.snapshot.compress.CompressTask;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.frame.datalayer.job.JobEntity;
import com.andcup.android.frame.datalayer.task.RxAsyncTask;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.tools.AndroidUtils;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/13.
 */
public class SnapshotFragment extends GateDialogFragment {

    public static final int PICK_PHOTO = 100;

    @Bind(R.id.gv_snapshot)
    GridView mGvSnapshot;
    @Restore(value = BundleKey.TASK_DETAIL)
    TaskDetailEntity mTaskDetail;

    TaskBusinessEntity mTaskBusinessEntity;

    SnapshotAdapter mAdapter;
    String          mMark;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_snapshot;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mAdapter = new SnapshotAdapter(getActivity());
        mGvSnapshot.setAdapter(mAdapter);
    }

    @OnClick(R.id.tv_cancel)
    public void onCancel(){
        dismissAllowingStateLoss();
    }

    @OnClick(R.id.tv_ok)
    public void onCommit() {
        List<String> imageList = mAdapter.getImageList();
        if( null == imageList || imageList.size() <= 0){
            SnackBar.make(getActivity(), getString(R.string.select_snapshot)).show();
            return;
        }
        showLoading();
        //start crop.
        cropAndUpload(imageList);
    }

    private List<String> cropAndUpload(List<String> imageList){
        new CompressTask(imageList){
            @Override
            public void onSuccess(List<String> imageList) {
                if( null != imageList && imageList.size() > 0){
                    upload(imageList);
                }
            }
        }.execute();
        return imageList;
    }

    private void upload(List<String> imageList){
        call(new JobUploadImages(mark(), imageList), new CallBack<JobEntity>() {
            @Override
            public void onSuccess(JobEntity jobEntity) {
                completeLocalTask();
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                hideLoading();
                SnackBar.make(getContext(), e.getMessage()).show();
            }
        });
    }

    private String mark(){
        if(TextUtils.isEmpty(mMark)){
            mMark = UserProvider.getInstance().getUid()  + "_" +
                    mTaskDetail.getTaskId() + "_" +
                    mTaskDetail.getTaskOptionId() + "_" + System.currentTimeMillis();
        }
        return mMark;
    }

    @Subscribe
    public void onAddSnapshot(AddSnapshotEvent event) {
        final String IMAGE_TYPE = "image/*.png";
        Intent pickAlbum = new Intent(Intent.ACTION_PICK, null);
        pickAlbum.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_TYPE);
        try {
            startActivityForResult(pickAlbum, PICK_PHOTO + event.getPosition());
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode >= PICK_PHOTO && requestCode <= PICK_PHOTO + 8) {
            String filepath = AndroidUtils.parseImagePathOnActivityResult(getActivity(), data);
            mAdapter.addSnapshot(requestCode - PICK_PHOTO, filepath);
        }
    }

    private void completeLocalTask(){
        if( null != mTaskDetail){
            mTaskBusinessEntity = TaskBusiness.Action.query(mTaskDetail.getTaskId(), mTaskDetail.getTaskOptionId());
            if(null != mTaskBusinessEntity){
                mTaskBusinessEntity.setMark(mark());
                completeTask();
            }else{
                end();
            }
        }
    }

    private void end(){
        hideLoading();
        dismissAllowingStateLoss();
        updateTaskDetail();
    }

    private void completeTask(){
        call(new JobTaskBusiness(mTaskBusinessEntity), new SimpleCallback<BaseEntity<CompleteInfoEntity>>() {
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
                TaskBusiness.Action.delete(mTaskBusinessEntity);
                updateTaskDetail();
                SnackBar.make(getActivity(), completeInfoEntityBaseEntity.getMessage()).show();
                //SnackBar.make(getActivity(), getString(R.string.task_complete_info, mTaskBusinessEntity.getScore())).show();
                hideLoading();
                dismissAllowingStateLoss();
            }
        });
    }

    private void updateTaskDetail() {
        call(new JobGetTaskDetails(mTaskDetail.getTaskId()), new SimpleCallback<BaseEntity<TaskDetailEntity>>() {
            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new BizException(e.getMessage()));
            }
        });
    }
}
