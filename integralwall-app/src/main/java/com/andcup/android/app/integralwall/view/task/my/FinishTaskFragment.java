package com.andcup.android.app.integralwall.view.task.my;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobGetMyQuickTasks;
import com.andcup.android.app.integralwall.datalayer.model.MyTaskQuickEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
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
 * Created by Amos on 2016/3/16.
 */
public class FinishTaskFragment extends BaseFragment {

    final int mPageSize = 20;
    int mPageIndex;
    int mTotalCount;

    @Bind(R.id.srv_refresh)
    SuperRecyclerView mSrvRefresh;
    FinishTaskAdapter mAdapter;
    boolean mIsRefresh = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quick_task_finish;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mSrvRefresh.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FinishTaskAdapter(getActivity());
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
                if (numberOfItems < mTotalCount) {
                    mPageIndex++;
                    call(mPageIndex, mPageSize);
                }else{
                    mSrvRefresh.hideMoreProgress();
                }
            }
        });
    }

    private void call(int pageIndex, int pageSize){
        call(new JobGetMyQuickTasks(pageIndex, pageSize), new SimpleCallback<BaseListEntry<MyTaskQuickEntity>>() {
            @Override
            public void onError(Throwable e) {
                mIsRefresh = true;
                setAdapter();
            }

            @Override
            public void onSuccess(BaseListEntry<MyTaskQuickEntity> quickTaskEntityBaseListEntry) {
                try{
                    mTotalCount = quickTaskEntityBaseListEntry.data().getTotalCount();
                }catch (Exception e){

                }
            }

            @Override
            public void onFinish() {
                mIsRefresh = true;
                setAdapter();
            }
        });
    }

    private void load(){
        loader(MyTaskQuickEntity.class, Where.user(), new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                notifyDataSetChanged(cursor);
            }
        });
    }

    private void notifyDataSetChanged(Cursor cursor){
        List<MyTaskQuickEntity> taskQuickEntities = SqlConvert.convertAll(cursor, MyTaskQuickEntity.class);
        setUpAdapter(taskQuickEntities);
        mAdapter.notifyDataSetChanged(taskQuickEntities);
    }

    private void setUpAdapter(List<MyTaskQuickEntity> tasks){
        if(mSrvRefresh.getAdapter() == null && (mIsRefresh || (null != tasks && tasks.size() > 0))){
            mSrvRefresh.setAdapter(mAdapter);
        }
    }

    private void setAdapter(){
        if(mSrvRefresh.getAdapter() == null && mTotalCount <= 0 ){
            mSrvRefresh.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }
}
