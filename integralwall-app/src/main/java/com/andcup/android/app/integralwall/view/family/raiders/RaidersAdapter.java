package com.andcup.android.app.integralwall.view.family.raiders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.RaidersEntity;
import com.andcup.android.app.integralwall.view.base.BaseAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;

import static com.andcup.android.app.integralwall.view.family.raiders.RaidersItem.TYPE_CONTENT;
import static com.andcup.android.app.integralwall.view.family.raiders.RaidersItem.TYPE_TITLE;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/24.
 */
public class RaidersAdapter extends BaseAdapter{

    List<RaidersItem> mItemList;

    public RaidersAdapter(Context context) {
        super(context);
    }

    public void notifyDataSetChanged(List<RaidersItem> list){
        mItemList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType){
            case TYPE_TITLE:
                return R.layout.list_item_raiders_title;
            case TYPE_CONTENT:
                return R.layout.list_item_raiders;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position).getType();
    }

    @Override
    public BaseViewHolder createViewHolder(View itemView, int viewType) {
        switch (viewType){
            case TYPE_TITLE:
                return new TitleViewHolder(itemView);
            case TYPE_CONTENT:
                return new ViewHolder(itemView);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return null == mItemList ? 0 : mItemList.size();
    }

    public class TitleViewHolder extends BaseViewHolder{

        @Bind(R.id.tv_title)
        TextView mTvTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            mTvTitle.setText((CharSequence) mItemList.get(position).getData());
        }
    }

    public class ViewHolder extends BaseViewHolder{

        @Bind(R.id.tv_title)
        TextView mTvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            RaidersEntity entity = (RaidersEntity) mItemList.get(position).getData();
            mTvTitle.setText(entity.getTitle());
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            RaidersEntity entity = (RaidersEntity) mItemList.get(mPosition).getData();
            EventBus.getDefault().post(entity);
        }
    }
}
