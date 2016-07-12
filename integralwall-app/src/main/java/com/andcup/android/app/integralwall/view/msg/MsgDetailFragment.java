package com.andcup.android.app.integralwall.view.msg;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.IntegralWallDataLayer;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobGetMsgDetail;
import com.andcup.android.app.integralwall.datalayer.model.MsgEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.htmltextview.HtmlTextView;
import com.yl.android.cpa.R;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/25.
 */
public class MsgDetailFragment extends BaseFragment{

    @Restore(BundleKey.MSG_ID)
    String mMsgId;

    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_content)
    HtmlTextView mTvContent;
    @Bind(R.id.tv_date)
    TextView mTvDate;
    MsgEntity mMsgEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_msg_detail;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        setTitle(R.string.msg_detail);
        call();
        loader();
    }

    private void call(){
        call(new JobGetMsgDetail(mMsgId), new SimpleCallback<BaseEntity<MsgEntity>>(){
            @Override
            public void onSuccess(BaseEntity<MsgEntity> msgEntityBaseEntity) {
                super.onSuccess(msgEntityBaseEntity);
                mMsgEntity = msgEntityBaseEntity.body();
                update();
            }
        });
    }

    private void loader(){
        loader(MsgEntity.class, Where.msgId(mMsgId), new SqlLoader.CallBack<MsgEntity>() {
            @Override
            public void onUpdate(MsgEntity msgEntity) {
                mMsgEntity = msgEntity;
                if( null != mMsgEntity){
                    update();
                }
            }
        });
    }

    private void update(){
        mTvTitle.setText(mMsgEntity.getTitle());
        mTvContent.setScaleType(HtmlTextView.ScaleType.FIT_WIDTH);
        mTvContent.setHtmlText(mMsgEntity.getContent());
        mTvDate.setText(mMsgEntity.getTime());
    }
}
