package com.andcup.android.app.integralwall.view.usercenter.score;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobScoreDetail;
import com.andcup.android.app.integralwall.datalayer.model.ScoreDetailEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.integralwall.commons.RecycleViewDivider;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.yl.android.cpa.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/23.
 */
public abstract class AbsScoreFragment extends BaseFragment {

    final int mPageSize = 20;
    int mPageIndex;
    int mTotalCount;

    @Bind(R.id.srv_refresh_score_in_out)
    SuperRecyclerView mSrvRefresh;
    ScoreAdapter mAdapter;
    DateFormat mDf = new SimpleDateFormat("yyyy-MM-dd");
    String mDate;
    boolean mIsRefresh = false;


    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mDate = mDf.format(new Date());
        mSrvRefresh.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ScoreAdapter(getActivity());
//        mSrvRefresh.setAdapter(mAdapter);
        mSrvRefresh.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.app_divider)));
        //int recycle view.
        setupOnRefreshListener();
        setupOnRefreshMoreListener();
        //load from remote
        call(mPageIndex, mPageSize);
        //load from local.
        load();
}

    private void setupOnRefreshListener() {
        mSrvRefresh.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex = 0;
                call(mPageIndex, mPageSize);
            }
        });
    }

    private void setupOnRefreshMoreListener() {
        mSrvRefresh.setNumberBeforeMoreIsCalled(1);
        mSrvRefresh.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
                if (numberOfItems + 1 < mTotalCount) {
                    mPageIndex++;
                    call(mPageIndex, mPageSize);
                } else {
                    mSrvRefresh.hideMoreProgress();
                }
            }
        });
    }

    private void call(int pageIndex, int pageSize) {
        call(new JobScoreDetail(pageIndex, pageSize, getType(), mDate), new SimpleCallback<BaseListEntry<ScoreDetailEntity>>() {
            @Override
            public void onError(Throwable e) {
                mIsRefresh = true;
                setAdapter();
            }

            @Override
            public void onSuccess(BaseListEntry<ScoreDetailEntity> scoreDetailEntityBaseListEntry) {
                try{
                    mTotalCount = scoreDetailEntityBaseListEntry.getBaseListData().getTotalCount();
                }catch (Exception e){

                }
                mIsRefresh = true;
                setAdapter();
            }
        });
    }

    private void load() {
        loader(ScoreDetailEntity.class, Where.ScoreDetail(getType()),
                new SqlLoader.CursorCallBack() {
                    @Override
                    public void onUpdate(Cursor cursor) {
                        notifyDataSetChanged(cursor);
                    }
                });
    }

    private void notifyDataSetChanged(Cursor cursor) {
        List<ScoreItem> ScoreItems = new ArrayList<>();
        List<ScoreDetailEntity> tasks = SqlConvert.convertAll(cursor, ScoreDetailEntity.class);
        for (int i = 0; null != tasks && i < tasks.size(); i++) {
            ScoreItem<ScoreDetailEntity> task = new ScoreItem<>(tasks.get(i));
            ScoreItems.add(task);
        }
        setUpAdapter(tasks);
        mAdapter.notifyDataSetChanged(ScoreItems);
    }

    private void setUpAdapter(List<ScoreDetailEntity> tasks){
        if(mSrvRefresh.getAdapter() == null && (mIsRefresh || (null != tasks && tasks.size() > 0))){
            mSrvRefresh.setAdapter(mAdapter);
        }
    }

    private void setAdapter(){
        if(mSrvRefresh.getAdapter() == null && mTotalCount <= 0){
            mSrvRefresh.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_score_in_out;
    }

    public abstract int getType();
}
