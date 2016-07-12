package com.andcup.android.app.integralwall.view.cash;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.CashingItemEntity;
import com.andcup.android.app.integralwall.event.CashItemEvent;
import com.andcup.android.app.integralwall.view.base.BaseAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/22.
 */
public class CashItemAdapter extends BaseAdapter{

    List<CashingItemEntity> mCashItemList;
    int mChecked = -1;

    public CashItemAdapter(Context context) {
        super(context);
    }

    public void notifyDataSetChanged(List<CashingItemEntity> itemEntities){
        mCashItemList = itemEntities;
        notifyDataSetChanged();
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_cashing;
    }

    @Override
    public ViewHolder createViewHolder(View itemView, int viewType) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return null == mCashItemList ? 0 : mCashItemList.size();
    }

    class ViewHolder extends BaseViewHolder{

        @Bind(R.id.tv_value)
        TextView mTvValue;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            mTvValue.setText(mCashItemList.get(position).getName());
            mTvValue.setEnabled(mChecked == position);
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            mChecked = mPosition;
            notifyDataSetChanged();
            CashingItemEntity entity = mCashItemList.get(mPosition);
            EventBus.getDefault().post(new CashItemEvent(entity.getExchangeId(), entity.getScore()));
        }
    }
}
