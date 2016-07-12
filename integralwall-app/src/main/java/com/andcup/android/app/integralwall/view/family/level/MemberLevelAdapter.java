package com.andcup.android.app.integralwall.view.family.level;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.MemberEntity;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.view.base.BaseAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.yl.android.cpa.R;

import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/23.
 */
public class MemberLevelAdapter extends BaseAdapter{

    List<MemberEntity> mLevel;

    public MemberLevelAdapter(Context context) {
        super(context);
    }

    public void notifyDataSetChanged(List<MemberEntity> level){
        mLevel = level;
        notifyDataSetChanged();
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_members;
    }

    @Override
    public ViewHolder createViewHolder(View itemView, int viewType) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return null == mLevel? 0:mLevel.size();
    }

    class ViewHolder extends BaseViewHolder{
        @Bind(R.id.tv_nick_name)
        TextView mTvNickName;
        @Bind(R.id.tv_join_days)
        TextView mTvJoinDays;
        @Bind(R.id.tv_complete_task)
        TextView mTvCompleteTask;
        @Bind(R.id.tv_offer_score)
        TextView mTvOfferScore;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            MemberEntity entity = mLevel.get(position);
            mTvNickName.setText(entity.getNickName());
            mTvJoinDays.setText(FormatString.newJoinDays(entity.getJoinDays()));
            mTvCompleteTask.setText(FormatString.newCompleteTasks(entity.getCompleteTaskNumber()));
            mTvOfferScore.setText(FormatString.newOfferScores(entity.getOfferScore()));
        }
    }
}
