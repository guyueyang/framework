package com.andcup.android.app.integralwall.view.usercenter.vip;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.ExperienceEntity;
import com.andcup.android.app.integralwall.view.base.BaseAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.yl.android.cpa.R;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/26.
 */
public class VipExperienceAdapter extends BaseAdapter {

    List<VipExperienceItem> mItems;

    public VipExperienceAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_exp;
    }

    @Override
    public BaseViewHolder createViewHolder(View itemView, int viewType) {
        return new TaskViewHolder(itemView);
    }


    public void notifyDataSetChanged(List<VipExperienceItem> data) {
        mItems = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return null == mItems ? 0 : mItems.size();
    }

    class TaskViewHolder extends BaseViewHolder implements View.OnClickListener{
        @Bind(R.id.tv_date)
        TextView mTvDate;
        @Bind(R.id.tv_from)
        TextView mTvFrom;
        @Bind(R.id.tv_value)
        TextView mTvValue;
        public TaskViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            ExperienceEntity task = (ExperienceEntity) mItems.get(mPosition).getData();
            mTvDate.setText(task.getGetTime());
            mTvValue.setText(task.getExperience());
            mTvFrom.setText(task.getSource());


        }

        @Override
        public void onClick(View view) {

        }
    }
}
