package com.andcup.android.app.integralwall.view.task.my;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.TaskQuickEntity;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.view.base.BaseAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.andcup.android.app.integralwall.tools.TaskHelper;
import com.andcup.android.integralwall.commons.widget.URIRoundedImageView;
import com.yl.android.cpa.R;

import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/16.
 */
public class TodoTaskAdapter extends BaseAdapter{

    List<TaskQuickEntity> mItems;

    public TodoTaskAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_task_quick;
    }

    @Override
    public BaseViewHolder createViewHolder(View itemView, int viewType) {
        return new TaskViewHolder(itemView);
    }

    public void notifyDataSetChanged(List<TaskQuickEntity> data){
        mItems = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return null == mItems ? 0 : mItems.size();
    }

    class TaskViewHolder extends BaseViewHolder{
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.tv_content)
        TextView mTvContent;
        @Bind(R.id.tv_score)
        TextView mTvScore;
        @Bind(R.id.riv_avatar)
        URIRoundedImageView mUriAvatar;

        public TaskViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            TaskQuickEntity task = mItems.get(mPosition);
            mTvTitle.setText(task.getName());
            mTvContent.setText(task.getIntro());

            String score = String.valueOf(task.getScore());
            String textScore = mContext.getResources().getString(R.string.format_score, score);
            mTvScore.setText(FormatString.newScore(textScore, score));
            mTvContent.setEnabled(!task.isComplete());
            int resource = task.isComplete()? R.string.task_today_is_complete:R.string.task_today_can_complete;
            mTvContent.setText(mContext.getResources().getString(resource));
            mUriAvatar.setImageURI(Uri.parse(task.getImageUrl()));
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            TaskQuickEntity task =  mItems.get(mPosition);
            TaskHelper.openTask(Integer.parseInt(task.getTaskId()));
        }
    }

}
