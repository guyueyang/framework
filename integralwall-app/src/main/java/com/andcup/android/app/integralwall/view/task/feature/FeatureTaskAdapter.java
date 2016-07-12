package com.andcup.android.app.integralwall.view.task.feature;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.TaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskFeatureEntity;
import com.andcup.android.app.integralwall.event.OpenUndoTaskEvent;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.view.base.BaseAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.andcup.android.app.integralwall.tools.TaskHelper;
import com.andcup.android.integralwall.commons.widget.URIRoundedImageView;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/16.
 */
public class FeatureTaskAdapter extends BaseAdapter{

    List<TaskFeatureEntity> mItems;

    public FeatureTaskAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_task;
    }

    @Override
    public BaseViewHolder createViewHolder(View itemView, int viewType) {
        return new TaskViewHolder(itemView);
    }

    public void notifyDataSetChanged(List<TaskFeatureEntity> data){
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
            TaskEntity task = mItems.get(mPosition);
            mTvTitle.setText(task.getName());
            mTvContent.setText(task.getIntro());

            String score = String.valueOf(task.getScore());
            String textScore = mContext.getResources().getString(R.string.format_score, score);
            mTvScore.setText(FormatString.newScore(textScore, score));
            mUriAvatar.setImageURI(Uri.parse(task.getImageUrl()));
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            TaskEntity task = mItems.get(mPosition);
            TaskHelper.openTask(Integer.parseInt(task.getTaskId()));
        }
    }
}
