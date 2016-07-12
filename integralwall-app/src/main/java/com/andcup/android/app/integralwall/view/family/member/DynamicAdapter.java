package com.andcup.android.app.integralwall.view.family.member;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.FamilyInfoEntity;
import com.andcup.android.app.integralwall.view.base.BaseAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.yl.android.cpa.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/19.
 */
public class DynamicAdapter extends BaseAdapter {

    List<FamilyInfoEntity.MemberFeed> mMemberFeed = new ArrayList<>();

    public DynamicAdapter(Context context) {
        super(context);
    }

    public void notifyDataSetChanged(List<FamilyInfoEntity.MemberFeed> memberFeeds){
        mMemberFeed.clear();
        if(memberFeeds.size() == 1){
            mMemberFeed.addAll(memberFeeds);
        }else{
            for(int i = 0; i< 800; i++){
                mMemberFeed.addAll(memberFeeds);
                if(mMemberFeed.size() >= 800){
                    break;
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_dynamic;
    }

    @Override
    public BaseViewHolder createViewHolder(View itemView, int viewType) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return null == mMemberFeed ? 0 : mMemberFeed.size();
    }

    class ViewHolder extends BaseViewHolder{

        @Bind(R.id.tv_nick_name)
        TextView mTvNickName;
        @Bind(R.id.tv_date)
        TextView mTvDate;
        @Bind(R.id.tv_content)
        TextView mTvContent;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            mTvNickName.setText(mMemberFeed.get(position).getNickName());
            mTvDate.setText(mMemberFeed.get(position).getDate());
            mTvContent.setText(mMemberFeed.get(position).getContent());
        }
    }
}
