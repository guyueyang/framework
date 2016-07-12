package com.andcup.android.app.integralwall.view.task.union;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobUnionPlatform;
import com.andcup.android.app.integralwall.datalayer.model.TaskUnionPlatformEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.RecycleViewDivider;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.yl.android.cpa.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/16.
 */
public class UnionTaskFragment extends BaseFragment {

    final int mPageSize = 20;
    int mPageIndex;
    int mTotalCount;

    @Bind(R.id.srv_refresh)
    SuperRecyclerView mSrvRefresh;
    UnionTaskAdapter mAdapter;

    @Restore(BundleKey.IS_UNION)
    String mIsUnion;
    boolean mIsRefresh = false;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_union_task;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);

        if(mIsUnion!=null){
            setTitle(R.string.union_task);
        }

        mSrvRefresh.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new UnionTaskAdapter(getActivity());
        mAdapter.UnionTaskAdapters(getActivity());
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
        call(new JobUnionPlatform(pageIndex, pageSize), new SimpleCallback<BaseListEntry<TaskUnionPlatformEntity>>() {
            @Override
            public void onError(Throwable e) {
                mIsRefresh = true;
                setAdapter();
            }

            @Override
            public void onSuccess(BaseListEntry<TaskUnionPlatformEntity> unionPlatformEntityBaseListEntry) {
                try{
                    mTotalCount = unionPlatformEntityBaseListEntry.getBaseListData().getTotalCount();
                }catch (Exception e){

                }
                mIsRefresh = true;
                setAdapter();
            }
        });
    }

    private void load() {
        loader(TaskUnionPlatformEntity.class,  new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                notifyDataSetChanged(cursor);
            }
        });
    }

    private void notifyDataSetChanged(Cursor cursor) {
        List<UnionTaskItem> unionTaskItems = new ArrayList<>();
        List<TaskUnionPlatformEntity> tasks = SqlConvert.convertAll(cursor, TaskUnionPlatformEntity.class);
        for (int i = 0; null != tasks && i < tasks.size(); i++) {
            UnionTaskItem<TaskUnionPlatformEntity> task = new UnionTaskItem<>(tasks.get(i));
            unionTaskItems.add(task);
        }
        setUpAdapter(tasks);
        mAdapter.notifyDataSetChanged(unionTaskItems);
    }

    private void setUpAdapter(List<TaskUnionPlatformEntity> tasks){
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
}
