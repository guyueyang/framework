package com.andcup.android.app.integralwall.view.task.detail;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.TaskBusinessEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskDetailEntity;
import com.andcup.android.app.integralwall.event.SnapshotEvent;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.view.base.BaseListAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.andcup.android.app.integralwall.view.task.biz.TaskBusiness;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/17.
 */
public class TaskOptionAdapter extends BaseListAdapter {

    List<QuickTaskItem> mTaskItems = new ArrayList<>();
    int mOptionId;
    String mOptionScore;
    TaskDetailEntity mTaskDetail;

    public TaskOptionAdapter(Context context) {
        super(context);
    }

    public void notifyDataSetChanged(int optionId, TaskDetailEntity taskDetail) {
        mTaskItems.clear();
        mTaskDetail = taskDetail;
        List<TaskDetailEntity.TaskOption> taskOptions = taskDetail.getTaskOptions();
        mOptionId = optionId;
        int mActiveIndex = -1;
        for (int i = 0; null != taskOptions && i < taskOptions.size(); i++) {
            QuickTaskItem item = QuickTaskItem.genOption(taskOptions.get(i));
            mTaskItems.add(item);
            int tempId = Integer.valueOf(taskOptions.get(i).getOptionId());
            if (optionId == -1) {
                /**所有的任务都已经做完*/
                item.setState(QuickTaskItem.State.Active);
            } else if (tempId == optionId) {
                /**将要做的任务-待激活*/
                mActiveIndex = i;
                /**今日已完成，则设置为不可做任务*/
                mOptionScore = taskOptions.get(i).getScore();
                if(taskDetail.isTodayCompleted()){
                   if(taskDetail.isOptionVarify()){
                       item.setState(QuickTaskItem.State.Verify);
                   }else{
                       item.setState( QuickTaskItem.State.Disable);
                   }
                }else{
                    item.setState( QuickTaskItem.State.Wait);
                }
            } else if (mActiveIndex == -1) {
                /**已经完成的任务.*/
                item.setState(QuickTaskItem.State.Active);
            } else {
                /**不可做的任务.*/
                item.setState(QuickTaskItem.State.Disable);
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        QuickTaskItem quickTaskItem = mTaskItems.get(position);
        return quickTaskItem.getTaskOption().isRequestScreenShot() ? 0 : 1;
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {
            case 0:
                return R.layout.list_item_task_option_snapshot;
            case 1:
            default:
                return R.layout.list_item_task_option;
        }
    }

    @Override
    public BaseViewHolder createViewHolder(View itemView, int viewType) {
        switch (viewType) {
            case 0:
                return new SnapshotViewHOlder(itemView);
            default:
                return new OptionViewHolder(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return null == mTaskItems ? 0 : mTaskItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mTaskItems.get(i);
    }

    class SnapshotViewHOlder extends OptionViewHolder{

        @Bind(R.id.btn_commit)
        Button mBtnCommit;

        public SnapshotViewHOlder(View itemView) {
            super(itemView);
            mBtnCommit.setOnClickListener(this);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            QuickTaskItem quickTaskItem = mTaskItems.get(position);
            String snapshot = quickTaskItem.getTaskOption().getScreenShotRequest();
            int status = quickTaskItem.getTaskOption().getStatus();
            mBtnCommit.setVisibility(View.VISIBLE);
            switch (status){
                case TaskDetailEntity.TaskOption.STATUS_NOT_COMPLETE:
                    TaskBusinessEntity entity = TaskBusiness.Action.query(mTaskDetail.getTaskId(), mTaskDetail.getTaskOptionId());
                    mBtnCommit.setEnabled( null != entity );
                    mBtnCommit.setText(snapshot);
                    break;
                case TaskDetailEntity.TaskOption.STATUS_VERIFY_FAILED:
                    mBtnCommit.setText(mBtnCommit.getContext().getResources().getString(R.string.task_verify_failed));
                    break;
                case TaskDetailEntity.TaskOption.STATUS_VERIFY:
                    mBtnCommit.setText(mBtnCommit.getContext().getResources().getString(R.string.task_verify));
                    mBtnCommit.setEnabled(false);
                default:
                    mBtnCommit.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            if(view == mBtnCommit){
                EventBus.getDefault().post(new SnapshotEvent());
            }
        }
    }

    class OptionViewHolder extends BaseViewHolder {
        @Bind(R.id.tv_number)
        TextView mTvNumber;
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.tv_score)
        TextView mTvScore;
        @Bind(R.id.tv_state)
        TextView mTvState;
        @Bind(R.id.iv_finish)
        ImageView mIvFinish;

        public OptionViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            mTvNumber.setText(String.valueOf(position + 1));
            mTvTitle.setText(Html.fromHtml(mTaskItems.get(position).getTitle()));
            mTvScore.setText(FormatString.newScore(mTaskItems.get(position).getScore()));

            QuickTaskItem taskItem = mTaskItems.get(position);
            QuickTaskItem.State state = taskItem.getState();
            mTvNumber.setEnabled(state == QuickTaskItem.State.Wait || state == QuickTaskItem.State.Verify);
            mTvTitle.setEnabled(state == QuickTaskItem.State.Wait || state == QuickTaskItem.State.Verify);
            mTvScore.setEnabled(state == QuickTaskItem.State.Wait || state == QuickTaskItem.State.Verify);
            mTvState.setEnabled(state == QuickTaskItem.State.Wait || state == QuickTaskItem.State.Verify);
            mIvFinish.setVisibility((state == QuickTaskItem.State.Active ||
                                     state == QuickTaskItem.State.Verify) ? View.VISIBLE : View.GONE);
            //任务已完成
            if (state == QuickTaskItem.State.Active) {
                mTvState.setText(mContext.getResources().getString(R.string.task_has_do));
                mTvState.setVisibility(View.VISIBLE);
            }
            //任务正在进行
            if (state == QuickTaskItem.State.Wait) {
                mTvState.setText(mContext.getResources().getString(R.string.task_can_do));
                mTvState.setVisibility(View.VISIBLE);
            }
            //任务不未激活
            if (state == QuickTaskItem.State.Disable) {
                mTvState.setVisibility(View.GONE);
            }
            if(state == QuickTaskItem.State.Verify){
                mTvState.setVisibility(View.VISIBLE);
                mIvFinish.setEnabled(false);
                mTvState.setText(mContext.getResources().getString(R.string.task_is_verify));
            }
        }
    }
}
