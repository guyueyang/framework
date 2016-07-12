package com.andcup.android.app.integralwall.view.msg;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobGetMsgList;
import com.andcup.android.app.integralwall.datalayer.model.MsgEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.frame.view.annotations.Restore;
import com.yl.android.cpa.R;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/10.
 */
public class MsgBarFragment extends BaseFragment{

    @Restore(value = BundleKey.TITLE)
    String mTitle;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_count)
    TextView mTvMsgCount;
    @Restore(value = BundleKey.CALL)
    boolean mCall = true;

    int mPageIndex = 0;
    int mPageSize  = 20;
    int mLoaderId = genLoaderId();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_action_bar;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTvTitle.setText(mTitle);
        load();
    }

    @Override
    public void onResume() {
        super.onResume();
        call();
        load();
    }

    private void call(){
        if(!mCall){
            return;
        }
        call(new JobGetMsgList(mPageIndex, mPageSize, MsgEntity.STATE_READ_NO), new SimpleCallback<BaseListEntry<MsgEntity>>());
    }

    private void load(){
        loader(mLoaderId, MsgEntity.class, Where.readed(MsgEntity.STATE_READ_NO), new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                List<MsgEntity> msg = SqlConvert.convertAll(cursor, MsgEntity.class);
                if( null != msg && msg.size() > 0){
                    mTvMsgCount.setVisibility(View.VISIBLE);
                    mTvMsgCount.setText(String.valueOf(msg.size()));
                }else{
                    mTvMsgCount.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick(R.id.rl_msg)
    public void onMsgClick(){
        go2(MsgFragment.class, null);
    }
}
