package com.andcup.android.app.integralwall.view.task.limit;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobGetFeatureTasks;
import com.andcup.android.app.integralwall.datalayer.job.JobGetLimitTasks;
import com.andcup.android.app.integralwall.datalayer.model.TaskFeatureEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskLimitEntity;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListData;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
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
 * Created by Amos on 2016/5/20.
 */
public class LimitTaskFragment extends BaseFragment {

    final int mPageSize = 20;
    int mPageIndex;
    int mTotalCount;

    @Bind(R.id.srv_refresh)
    SuperRecyclerView mSrvRefresh;

    LimitTaskAdapter mAdapter;

    boolean mIsRefresh = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_limit_task;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mSrvRefresh.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new LimitTaskAdapter(getActivity());
        mSrvRefresh.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.app_divider)));
        //int recycle view.
        setupOnRefreshListener();
        setupOnRefreshMoreListener();
        //load from local.
        load();
    }

    @Override
    public void onResume() {
        super.onResume();
        //load from remote
        call(mPageIndex, mPageSize);
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
                } else {
                    mSrvRefresh.hideMoreProgress();
                }
            }
        });
    }

    private void call(int pageIndex, int pageSize) {
        call(new JobGetLimitTasks(pageIndex, pageSize), new SimpleCallback<BaseListEntry<TaskLimitEntity>>() {
            @Override
            public void onError(Throwable e) {
                mIsRefresh = true;
                setAdapter();
            }

            @Override
            public void onSuccess(BaseListEntry<TaskLimitEntity> quickTaskEntityBaseListEntry) {
                try {
                    BaseListData<TaskLimitEntity> data = quickTaskEntityBaseListEntry.data();
                    mTotalCount = data.getTotalCount();
                } catch (Exception e) {
                }
                mIsRefresh = true;
                setAdapter();
            }
        });
    }

    private void load() {
        loader(TaskLimitEntity.class, Where.user(), new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                notifyDataSetChanged(cursor);
            }
        });
    }

    private void notifyDataSetChanged(Cursor cursor) {
        List<TaskLimitEntity> tasks = SqlConvert.convertAll(cursor, TaskLimitEntity.class);
        setUpAdapter(tasks);
        mAdapter.notifyDataSetChanged(map(tasks));
    }

    private void setUpAdapter(List<TaskLimitEntity> tasks) {
        if (mSrvRefresh.getAdapter() == null && (mIsRefresh || (null != tasks && tasks.size() > 0))) {
            mSrvRefresh.setAdapter(mAdapter);
        }
    }

    private void setAdapter(){
        if( null == mSrvRefresh.getAdapter() && mTotalCount <= 0){
            mSrvRefresh.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    private List<LimitTaskItem> map(List<TaskLimitEntity> limitEntities){
        List<LimitTaskItem> item = new ArrayList<>();
        boolean isFirstStart = false;
        for(int i = 0; null != limitEntities && i < limitEntities.size(); i++){
            TaskLimitEntity entity = limitEntities.get(i);
            if(!entity.isStart() && !isFirstStart){
                isFirstStart = true;
                LimitTaskItem<String> title = new LimitTaskItem<>(null, LimitTaskItem.TITLE);
                item.add(title);
            }
            LimitTaskItem<TaskLimitEntity> listItem = new LimitTaskItem<>(entity, LimitTaskItem.CONTENT);
            item.add(listItem);
        }
        return item;
    }
}
