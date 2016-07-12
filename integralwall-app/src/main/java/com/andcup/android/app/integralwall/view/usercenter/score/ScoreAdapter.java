package com.andcup.android.app.integralwall.view.usercenter.score;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.ScoreDetailEntity;
import com.andcup.android.app.integralwall.view.base.BaseAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.yl.android.cpa.R;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/23.
 */
public class ScoreAdapter extends BaseAdapter{

    List<ScoreItem> mItems;
    Activity mActivity;

    public ScoreAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_score_detail;
    }

    @Override
    public BaseViewHolder createViewHolder(View itemView, int viewType) {
        return new TaskViewHolder(itemView);
    }


    public void notifyDataSetChanged(List<ScoreItem> data){
        mItems = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return null == mItems ? 0 : mItems.size();
    }

    class TaskViewHolder extends BaseViewHolder {
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.tv_content)
        TextView mTvContent;
        @Bind(R.id.tv_score)
        TextView mTvScore;

        public TaskViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            ScoreDetailEntity task = (ScoreDetailEntity) mItems.get(mPosition).getData();
            mTvTitle.setText(task.getName());
            mTvContent.setText(task.getAddTime());
            mTvScore.setText(task.getScore());
        }

        @Override
        public void onClick(View view) {

        }
    }

}
