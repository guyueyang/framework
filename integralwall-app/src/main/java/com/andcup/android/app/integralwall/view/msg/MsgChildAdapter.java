package com.andcup.android.app.integralwall.view.msg;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.MsgEntity;
import com.andcup.android.app.integralwall.view.base.BaseAdapter;
import com.andcup.android.app.integralwall.view.base.BaseViewHolder;
import com.andcup.android.integralwall.commons.htmltextview.HtmlTextView;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/23.
 */
public class MsgChildAdapter extends BaseAdapter{

    List<MsgEntity> mMsgList;

    public MsgChildAdapter(Context context) {
        super(context);
    }

    public void notifyDataSetChanged(List<MsgEntity> msgEntities){
        mMsgList = msgEntities;
        notifyDataSetChanged();
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_msg;
    }

    @Override
    public ViewHolder createViewHolder(View itemView, int viewType) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return null == mMsgList ? 0 : mMsgList.size();
    }

    class ViewHolder extends BaseViewHolder{

        @Bind(R.id.iv_avatar)
        ImageButton mBtnAvatar;
        @Bind(R.id.tv_title)
        TextView  mTvTitle;
        @Bind(R.id.tv_content)
        HtmlTextView mTvContent;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(int position) {
            super.onBindView(position);
            MsgEntity msgEntity = mMsgList.get(position);
            mBtnAvatar.setEnabled(msgEntity.isSystemMsg());
            mTvTitle.setText(msgEntity.getTitle());
            mTvContent.setScaleType(HtmlTextView.ScaleType.NONE);
            mTvContent.setHtmlText(msgEntity.getContent());
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            EventBus.getDefault().post(mMsgList.get(mPosition));
        }
    }
}
