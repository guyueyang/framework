package com.andcup.android.app.integralwall.view.home.rank;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.RankEntity;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.view.base.BaseAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.andcup.android.integralwall.commons.widget.URIRoundedImageView;
import com.yl.android.cpa.R;

import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/23.
 */
public class RankChildAdapter extends BaseAdapter {

    List<RankEntity> mRankList;

    public RankChildAdapter(Context context) {
        super(context);
    }

    public void notifyDataSetChanged(List<RankEntity> rankEntityList) {
        mRankList = rankEntityList;
        notifyDataSetChanged();
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_rank;
    }

    @Override
    public ViewHolder createViewHolder(View itemView, int viewType) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return null == mRankList ? 0 : mRankList.size();
    }

    class ViewHolder extends BaseViewHolder {

        @Bind(R.id.riv_avatar)
        URIRoundedImageView mUriAvatar;
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.tv_score)
        TextView mTvScore;
        @Bind(R.id.tv_rank)
        TextView mTvRank;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            RankEntity rankEntity = mRankList.get(position);
            mUriAvatar.setScaleType(ImageView.ScaleType.FIT_CENTER);
            mUriAvatar.setImageURI(Uri.parse(rankEntity.getAvatar()));
            mTvTitle.setText(rankEntity.getNickName());
            mTvScore.setText(FormatString.newRank(rankEntity.getScore()));
            switch (position) {
                case 0:
                    mTvRank.setBackgroundResource(R.drawable.shape_rank_1);
                    break;
                case 1:
                    mTvRank.setBackgroundResource(R.drawable.shape_rank_2);
                    break;
                case 2:
                    mTvRank.setBackgroundResource(R.drawable.shape_rank_3);
                    break;
                default:
                    mTvRank.setBackgroundResource(R.drawable.shape_rank_4);
                    break;
            }
            mTvRank.setText(String.valueOf(position + 1));
        }
    }
}
