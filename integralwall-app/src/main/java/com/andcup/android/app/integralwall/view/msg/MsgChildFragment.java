package com.andcup.android.app.integralwall.view.msg;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

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
import com.andcup.android.integralwall.commons.RecycleViewDivider;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.yl.android.cpa.R;

import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/23.
 */
public class MsgChildFragment extends BaseFragment{

    final int mPageSize = 20;
    int mPageIndex;
    int mTotalCount;

    @Restore(BundleKey.MSG_STATE)
    String mState;
    @Bind(R.id.srv_refresh)
    SuperRecyclerView mRvMsg;
    int mLoaderId = genLoaderId();

    MsgChildAdapter mAdapter;
    boolean mIsRefresh = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_msg_child;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mRvMsg.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MsgChildAdapter(getActivity());
        //mRvMsg.setAdapter(mAdapter);
        mRvMsg.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.app_divider)));
        setupOnRefreshListener();
        setupOnRefreshMoreListener();
        call(mPageIndex, mPageSize);
        load();
    }

    private void setupOnRefreshListener() {
        mRvMsg.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex = 0;
                call(mPageIndex, mPageSize);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    private void setupOnRefreshMoreListener() {
        mRvMsg.setNumberBeforeMoreIsCalled(1);
        mRvMsg.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
                if (numberOfItems + 1 < mTotalCount) {
                    mPageIndex++;
                    call(mPageIndex, mPageSize);
                }else{
                    mRvMsg.hideMoreProgress();
                }
            }
        });
    }

    private void call(int pageIndex, int pageSize){
        call(new JobGetMsgList(pageIndex, pageSize, Integer.parseInt(mState)), new SimpleCallback<BaseListEntry<MsgEntity>>() {
            @Override
            public void onError(Throwable e) {
                mIsRefresh = true;
                setAdapter();
            }

            @Override
            public void onSuccess(BaseListEntry<MsgEntity> msgEntityBaseListEntry) {
                try{
                    mTotalCount = msgEntityBaseListEntry.getBaseListData().getTotalCount();
                }catch (Exception e){
                }
                mIsRefresh = true;
                setAdapter();
            }
        });
    }

    private void load(){
        loader(mLoaderId, MsgEntity.class, Where.readed(Integer.parseInt(mState)), new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                List<MsgEntity> msgEntities = SqlConvert.convertAll(cursor, MsgEntity.class);
                if(null == mRvMsg.getAdapter() && (mIsRefresh || (null != msgEntities && msgEntities.size() > 0))){
                    mRvMsg.setAdapter(mAdapter);
                }
                mAdapter.notifyDataSetChanged(msgEntities);
            }
        });
    }

    private void setAdapter(){
        if(null == mRvMsg.getAdapter() && mTotalCount <= 0){
            mRvMsg.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }
}
