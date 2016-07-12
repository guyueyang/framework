package com.andcup.android.app.integralwall.view.task.limit;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.TaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskLimitEntity;
import com.andcup.android.app.integralwall.datalayer.tools.TimeUtils;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.tools.TaskHelper;
import com.andcup.android.app.integralwall.view.base.BaseAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.andcup.android.integralwall.commons.widget.URIRoundedImageView;
import com.yl.android.cpa.R;

import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/20.
 */
public class LimitTaskAdapter extends BaseAdapter {

    List<LimitTaskItem> mTaskList;

    public LimitTaskAdapter(Context context) {
        super(context);
    }

    public void notifyDataSetChanged(List<LimitTaskItem> taskItems){
        mTaskList = taskItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mTaskList.get(position).getType();
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType){
            case LimitTaskItem.TITLE:
                return R.layout.list_item_title_limit;
            case LimitTaskItem.CONTENT:
            default:
                return R.layout.list_item_task;
        }
    }

    @Override
    public BaseViewHolder createViewHolder(View itemView, int viewType) {
        switch (viewType){
            case LimitTaskItem.TITLE:
                return new TitleViewHolder(itemView);
            case LimitTaskItem.CONTENT:
            default:
                return new ViewHolder(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return null == mTaskList ? 0 : mTaskList.size();
    }

    class TitleViewHolder extends BaseViewHolder{

        public TitleViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ViewHolder extends BaseViewHolder{
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.tv_content)
        TextView mTvContent;
        @Bind(R.id.tv_score)
        TextView mTvScore;
        @Bind(R.id.riv_avatar)
        URIRoundedImageView mUriAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            TaskLimitEntity task = (TaskLimitEntity) mTaskList.get(mPosition).body();
            mTvTitle.setText(task.getName());
            if(!task.isStart()){
                String time = TimeUtils.getDate(Long.parseLong(task.getStartTime()));
                mTvContent.setText(mContext.getResources().getString(R.string.limit_task_content, time, task.getTotalQuota()));
            }else{
                mTvContent.setText(task.getIntro());
            }
            String score = String.valueOf(task.getScore());
            String textScore = mContext.getResources().getString(R.string.format_score, score);
            mTvScore.setText(FormatString.newScore(textScore, score));
            mUriAvatar.setImageURI(Uri.parse(task.getImageUrl()));
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            TaskLimitEntity task = (TaskLimitEntity) mTaskList.get(mPosition).body();
            if(task.isStart()){
                TaskHelper.openTask(Integer.parseInt(task.getTaskId()));
            }else{
                SnackBar.make(mContext, mContext.getResources().getString(R.string.task_is_not_start)).show();
            }
        }
    }
}
