package com.andcup.android.app.integralwall.view.home;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobGetHotTask;
import com.andcup.android.app.integralwall.datalayer.model.HotTaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.third.SdkManager;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.msg.MsgBarFragment;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.integralwall.commons.RecycleViewDivider;
import com.yl.android.cpa.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/10.
 */
public class NestHomeFragment extends BaseFragment {

    List<HomeItem> mHomeItem = new ArrayList<>();

    @Bind(R.id.srv_home)
    RecyclerView mSrvHome;

    HomeAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_nest;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        SdkManager.INST.mUid = UserProvider.getInstance().getUid();
        // show title.
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.TITLE, getString(R.string.app_name));
        bundle.putBoolean(BundleKey.CALL, true);
        go(MsgBarFragment.class, R.id.fr_title, bundle);
        // show header.
        go(HomeNavFragment.class, R.id.fr_header);
        mSrvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRvHeader.attachTo(mSrvHome.getRecyclerView(), true);
        mAdapter = new HomeAdapter(getActivity());
        mSrvHome.setAdapter(mAdapter);
        //set divider.
        mSrvHome.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.app_divider)));
        //add default home item.
        addDefaultHomeItem();
        //monitor database.
        loader(HotTaskEntity.class, (SqlLoader.CursorCallBack) new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                List<HotTaskEntity> hotTasks = SqlConvert.convert(cursor, HotTaskEntity.class, 10);
                if (null != hotTasks) {
                    addDefaultHomeItem();
                    //convert hot task to home item.
                    NestHomeFragment.this.clearHomeTypeItem(HomeItem.TYPE_TASK);
                    int taskTypeIndex = NestHomeFragment.this.getHomeTypeItem(HomeItem.TYPE_TITLE) + 2;
                    for (int i = 0; i < hotTasks.size(); i++) {
                        HomeItem<HotTaskEntity> hotTaskHomeItem = new HomeItem<>(HomeItem.TYPE_TASK);
                        hotTaskHomeItem.setData(hotTasks.get(i));
                        mHomeItem.add(taskTypeIndex + i, hotTaskHomeItem);
                    }
                }
                mAdapter.notifyDataSetChanged(mHomeItem);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        call();
    }

    private void call(){
        call(new JobGetHotTask(), new SimpleCallback<BaseListEntry<HotTaskEntity>>());
    }

    private void addDefaultHomeItem(){
        mHomeItem.clear();
        HomeItem<String> item = new HomeItem<>(HomeItem.TYPE_TITLE);
        item.setData(getResources().getString(R.string.hot_task));
        mHomeItem.add(item);
        //load new reward.
        if(!UserProvider.getInstance().isFirstLandingFinish()){
            item = new HomeItem<>(HomeItem.TYPE_NEW_USER_REWARD);
            item.setData(String.valueOf(HomeItem.TYPE_NEW_USER_REWARD));
        }else{
            item = new HomeItem<>(HomeItem.TYPE_DAILY_TASK);
            item.setData(String.valueOf(HomeItem.TYPE_DAILY_TASK));
        }
        mHomeItem.add(item);
        //load more.
        item = new HomeItem<>(HomeItem.TYPE_MORE);
        item.setData(getString(R.string.view_more));
        mHomeItem.add(item);
    }

    private int  getHomeTypeItem(int type){
        for(int i = 0; i< mHomeItem.size(); i++){
            if(mHomeItem.get(i).getType() == type){
                return i;
            }
        }
        return 0;
    }

    private void clearHomeTypeItem(int type){
        Iterator<HomeItem> homeItemIterator = mHomeItem.iterator();
        while (homeItemIterator.hasNext()){
            HomeItem homeItem = homeItemIterator.next();
            if(homeItem.getType() == type){
                homeItemIterator.remove();
            }
        }
    }
}
