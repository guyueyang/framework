package com.andcup.android.app.integralwall.view.guide;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.FirstLandingTaskEntity;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.view.base.BaseListAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.yl.android.cpa.R;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/22.
 */
public class NewUserTaskAdapter extends BaseListAdapter{

    List<FirstLandingTaskEntity> mTaskItems = new ArrayList<>();

    public NewUserTaskAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_task_newbie;
    }

    @Override
    public BaseViewHolder createViewHolder(View itemView, int viewType) {
        return new NewbieViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return null == mTaskItems ? 0 : mTaskItems.size();
    }

    public void notifyDataSetChanged(List<FirstLandingTaskEntity> TaskItems){
        mTaskItems=TaskItems;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return mTaskItems.get(position);
    }

    class NewbieViewHolder extends BaseViewHolder {
        @Bind(R.id.tv_number)
        TextView mTvNumber;
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.tv_score)
        TextView mTvScore;
        @Bind(R.id.iv_finish)
        ImageView mIvFinish;
        @Bind(R.id.btn_receive)
        Button mBtnReceive;
        @Bind(R.id.rl_first_landing)
        RelativeLayout mRlFirstLanding;
        public NewbieViewHolder(View itemView) {
            super(itemView);
            mBtnReceive.setOnClickListener(this);
            mRlFirstLanding.setOnClickListener(this);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            mTvNumber.setText(String.valueOf(position + 1));
            mTvTitle.setText(Html.fromHtml(mTaskItems.get(position).getTitle()));
            mTvScore.setText(FormatString.newScore(mTaskItems.get(position).getScore()));
            mIvFinish.setVisibility(View.GONE);
            mBtnReceive.setVisibility(View.GONE);
            if(position==0){
                mTvScore.setVisibility(View.GONE);
                mBtnReceive.setVisibility(View.VISIBLE);
            }
            if(position==5){
                mTvScore.setVisibility(View.GONE);
            }

            if(mTaskItems.get(position).getIsComplete().equals("1")){
                if(position==0){
                    mTvNumber.setEnabled(false);
                    mTvTitle.setEnabled(false);
                    mTvScore.setEnabled(false);
                    mBtnReceive.setEnabled(false);
                    mBtnReceive.setVisibility(View.GONE);
                    mIvFinish.setVisibility(View.VISIBLE);
                }else {
                    mTvNumber.setEnabled(false);
                    mTvTitle.setEnabled(false);
                    mTvScore.setEnabled(false);
                    mIvFinish.setVisibility(View.VISIBLE);
                    mRlFirstLanding.setEnabled(false);
                }
            }
        }

        @Override
        public void onClick(View view){
            switch (view.getId()) {
                case R.id.btn_receive:
//                    mFirstLandingTaskFragment.receivedScore();
                    EventBus.getDefault().post(mTaskItems.get(mPosition));
                    break;
                case R.id.rl_first_landing:
                    EventBus.getDefault().post(mTaskItems.get(mPosition));
//                    mFirstLandingTaskFragment.ToFirstCompleteTask(mTaskItems.get(mPosition).getmId(),mTaskItems.get(mPosition).getTaskId());
                    break;
                default:
                    break;
            }
        }


    }


}
