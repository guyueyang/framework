package com.andcup.android.app.integralwall.view.task.feature;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobGetFeatureTasks;
import com.andcup.android.app.integralwall.datalayer.model.TaskFeatureEntity;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListData;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.navigator.NavigatorItem;
import com.andcup.android.app.integralwall.view.task.my.TodoTaskFragment;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.RecycleViewDivider;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/16.
 */
public class FeatureTaskFragment extends BaseFragment {

    final int mPageSize = 20;
    int mPageIndex;
    int mTotalCount;

    @Bind(R.id.srv_refresh)
    SuperRecyclerView mSrvRefresh;
    @Bind(R.id.tv_wait_finish)
    TextView mTvWaitFinish;
    @Bind(R.id.include_wait_finish)
    View mWaitFinish;
    @Restore(BundleKey.IS_UNION)
    String mIsUnion;

    FeatureTaskAdapter mAdapter;
    boolean mIsRefresh = false;
    int mWaitFinishCount = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quick_task;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        if(mIsUnion!=null){
            setTitle(R.string.quick_task);
        }
        mSrvRefresh.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FeatureTaskAdapter(getActivity());
        //mSrvRefresh.setAdapter(mAdapter);
        mSrvRefresh.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.app_divider)));
        //int recycle view.
        setupOnRefreshListener();
        setupOnRefreshMoreListener();
        //load from local.
        load();
        mWaitFinishCount = UserProvider.getInstance().getUserInfo().getNumberOfNotCompletedTask();
        setWaitFinish();
        //loadWaitFinish();

        monitorUserInfo();
    }

    private void monitorUserInfo(){
        loader(UserInfoEntity.class, Where.user(), new SqlLoader.CallBack<UserInfoEntity>() {
            @Override
            public void onUpdate(UserInfoEntity userInfoEntity) {
                if(null != userInfoEntity){
                    mWaitFinishCount = userInfoEntity.getNumberOfNotCompletedTask();
                    setWaitFinish();
                }
            }
        });
    }

    private void setWaitFinish(){
        mTvWaitFinish.setText(String.valueOf(mWaitFinishCount));
        if(mWaitFinishCount <= 0){
            mWaitFinish.setVisibility(View.GONE);
        }else {
            mWaitFinish.setVisibility(View.VISIBLE);
        }
    }

//    @Subscribe
//    public void onNavigatorItemCheckedChenaged(NavigatorItem event){
//        if(event == NavigatorItem.Task){
//            //重新刷新.
//            mWaitFinishCount = UserProvider.getInstance().getUserInfo().getNumberOfNotCompletedTask();
//            setWaitFinish();
//        }
//    }

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
        call(new JobGetFeatureTasks(pageIndex, pageSize), new SimpleCallback<BaseListEntry<TaskFeatureEntity>>() {
            @Override
            public void onError(Throwable e) {
                mIsRefresh = true;
                setAdapter();
            }

            @Override
            public void onSuccess(BaseListEntry<TaskFeatureEntity> quickTaskEntityBaseListEntry) {
                try {
                    BaseListData<TaskFeatureEntity> data = quickTaskEntityBaseListEntry.data();
                    mTotalCount = data.getTotalCount();
                } catch (Exception e) {
                }
                mIsRefresh = true;
                setAdapter();
            }
        });
    }

    private void load() {
        loader(TaskFeatureEntity.class, Where.user(), new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                notifyDataSetChanged(cursor);
            }
        });
    }

    private void notifyDataSetChanged(Cursor cursor) {
        List<TaskFeatureEntity> tasks = SqlConvert.convertAll(cursor, TaskFeatureEntity.class);
        setUpAdapter(tasks);
        mAdapter.notifyDataSetChanged(tasks);
    }

    private void setUpAdapter(List<TaskFeatureEntity> tasks) {
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

    @OnClick(R.id.include_wait_finish)
    public void onEvent() {
        go2(TodoTaskFragment.class, null);
    }
}
