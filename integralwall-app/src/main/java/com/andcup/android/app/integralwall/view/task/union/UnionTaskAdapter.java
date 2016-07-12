package com.andcup.android.app.integralwall.view.task.union;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import com.andcup.android.app.integralwall.datalayer.model.TaskUnionPlatformEntity;
import com.andcup.android.app.integralwall.third.tools.UnionTask;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.view.base.BaseAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.andcup.android.integralwall.commons.widget.URIRoundedImageView;
import com.newqm.pointwall.QEarnNotifier;
import com.yl.android.cpa.R;
import java.util.List;
import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/17.
 */
public class UnionTaskAdapter extends BaseAdapter{

    List<UnionTaskItem> mItems;
    Activity mActivity;

    public UnionTaskAdapter(Context context) {
        super(context);
    }
    public void  UnionTaskAdapters(Activity activity){
        mActivity=activity;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_task;
    }

    @Override
    public BaseViewHolder createViewHolder(View itemView, int viewType) {
        return new TaskViewHolder(itemView);
    }


    public void notifyDataSetChanged(List<UnionTaskItem> data){
        mItems = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return null == mItems ? 0 : mItems.size();
    }

    class TaskViewHolder extends BaseViewHolder implements View.OnClickListener,QEarnNotifier {
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
            TaskUnionPlatformEntity task = (TaskUnionPlatformEntity) mItems.get(mPosition).getData();
            mTvTitle.setText(task.getmTaskName());
            mTvContent.setText(task.getmTaskNumber());
            String score = String.valueOf(task.getmTotalScore());
            String textScore = mContext.getResources().getString(R.string.format_score, score);
            mTvScore.setText(FormatString.newScore(textScore, score));
            mUriAvatar.setEditImageURI(Uri.parse(task.getmIcon()));
        }

        @Override
        public void onClick(View view) {
            TaskUnionPlatformEntity task = (TaskUnionPlatformEntity) mItems.get(mPosition).getData();
            UnionTask unionTask=new UnionTask(mContext,mActivity,this,task.getmTaskId());
        }

        @Override
        public void getPoints(int i) {

        }

        @Override
        public void getPointsFailed(String s) {

        }

        @Override
        public void earnedPoints(int i, int i1) {

        }
    }

}
