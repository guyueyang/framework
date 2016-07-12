package com.andcup.android.app.integralwall.view.family.raiders;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobGetRadiers;
import com.andcup.android.app.integralwall.datalayer.model.RaidersEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.tools.LinkViewHelper;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.integralwall.commons.RecycleViewDivider;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/16.
 */
public class RaidersFragment extends BaseFragment{

    @Bind(R.id.srv_refresh)
    SuperRecyclerView mSrvRaiders;
    RaidersAdapter    mAdapter;
    boolean mIsRefresh = false;
    int mTotalCount;

    List<RaidersItem> mItemList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_raiders;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mAdapter = new RaidersAdapter(getActivity());
        mSrvRaiders.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mSrvRaiders.setAdapter(mAdapter);
        mSrvRaiders.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.app_divider)));
        mSrvRaiders.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                call();
            }
        });
        call();
        load();
    }

    private void call(){
        call(new JobGetRadiers(), new SimpleCallback<BaseListEntry<RaidersEntity>>() {

            @Override
            public void onSuccess(BaseListEntry<RaidersEntity> raidersEntityBaseListEntry) {
                try{
                    mTotalCount = raidersEntityBaseListEntry.getBaseListData().getTotalCount();
                }catch (Exception e){
                }
                mIsRefresh = true;
                setAdapter();
            }

            @Override
            public void onError(Throwable e) {
                mIsRefresh = true;
                setAdapter();
            }
        });
    }

    private void load(){
        loader(RaidersEntity.class, new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                genRaidersItem(SqlConvert.convertAll(cursor,RaidersEntity.class ));
                setUpAdapter();
                mAdapter.notifyDataSetChanged(mItemList);
            }
        });
    }

    private void setUpAdapter(){
        if(mSrvRaiders.getAdapter() == null && (mIsRefresh || (null != mItemList && mItemList.size() > 2))){
            mSrvRaiders.setAdapter(mAdapter);
        }
    }

    private void setAdapter(){
        if(mSrvRaiders.getAdapter() == null && mTotalCount <= 0){
            mSrvRaiders.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void addRaidersItem(int string){
        RaidersItem<String> item = new RaidersItem<>(RaidersItem.TYPE_TITLE);
        item.setData(getResources().getString(string));
        mItemList.add(item);
    }

    private void genRaidersItem(List<RaidersEntity>raidersEntities){
        mItemList.clear();
        boolean added = false;
        addRaidersItem(R.string.raiders_new_user);
        if( null == raidersEntities){
            addRaidersItem(R.string.raiders_super_user);
            return;
        }
        for(int i = 0; null != raidersEntities && i< raidersEntities.size(); i++){
            RaidersItem<RaidersEntity> item = new RaidersItem<>(RaidersItem.TYPE_CONTENT);
            item.setData(raidersEntities.get(i));
            if(raidersEntities.get(i).isSuperUser() && !added){
                added = true;
                addRaidersItem(R.string.raiders_super_user);
            }
            mItemList.add(item);
        }
    }

    @Subscribe
    public void openURL(RaidersEntity raidersEntity){
        LinkViewHelper.open(getActivity(), raidersEntity.getUrl(), raidersEntity.getTitle());
    }
}
