package com.andcup.android.app.integralwall.view.task.my;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobGetQuickTasks;
import com.andcup.android.app.integralwall.datalayer.model.TaskQuickEntity;
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
 * Created by Amos on 2016/3/17.
 */
public class TodoTaskFragment extends BaseFragment {

    final int mPageSize = 20;
    int mPageIndex;
    int mTotalCount;

    @Bind(R.id.srv_refresh)
    SuperRecyclerView mSrvRefresh;
    @Restore(BundleKey.TITLE)
    boolean mShowTitle = true;
    TodoTaskAdapter mAdapter;
    boolean mIsRefresh = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_todo_task;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        if (mShowTitle) {
            setTitle(R.string.wait_finish_task);
        }
        mSrvRefresh.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TodoTaskAdapter(getActivity());
        //mSrvRefresh.setAdapter(mAdapter);
        mSrvRefresh.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.app_divider)));
        //mSrvRefresh.showProgress();
        //int recycle view.
        setupOnRefreshListener();
        setupOnRefreshMoreListener();
        load();
    }

    @Override
    public void onResume() {
        super.onResume();
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
        call(new JobGetQuickTasks(pageIndex, pageSize), new SimpleCallback<BaseListEntry<TaskQuickEntity>>() {
            @Override
            public void onError(Throwable e) {
                mIsRefresh = true;
                setAdapter();
            }

            @Override
            public void onSuccess(BaseListEntry<TaskQuickEntity> taskQuickEntityBaseListEntry) {
                mIsRefresh = true;
                try {
                    mTotalCount = taskQuickEntityBaseListEntry.data().getTotalCount();
                } catch (Exception e) {
                }
                setAdapter();
            }
        });
    }

    private void load() {
        loader(TaskQuickEntity.class, Where.user(), new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                List<TaskQuickEntity> tasks = SqlConvert.convertAll(cursor, TaskQuickEntity.class);
                if (mSrvRefresh.getAdapter() == null && (mIsRefresh || (null != tasks && tasks.size() > 0))) {
                    mSrvRefresh.setAdapter(mAdapter);
                }
                mAdapter.notifyDataSetChanged(tasks);

            }
        });
    }

    private void setAdapter() {
        if (null == mSrvRefresh.getAdapter() && mTotalCount <= 0) {
            mSrvRefresh.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }
}
