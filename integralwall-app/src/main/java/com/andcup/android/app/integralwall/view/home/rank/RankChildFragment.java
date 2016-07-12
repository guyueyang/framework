package com.andcup.android.app.integralwall.view.home.rank;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobGetRankList;
import com.andcup.android.app.integralwall.datalayer.model.RankEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.RecycleViewDivider;
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
public class RankChildFragment extends BaseFragment{

    @Restore(BundleKey.RANK)
    Rank mRank;
    @Bind(R.id.rv_rank)
    SuperRecyclerView mRvRank;
    @Bind(R.id.tv_score)
    TextView mTvScore;
    RankChildAdapter mRankAdapter;
    boolean  mIsRefresh = false;
    int mTotalCount;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rank_child;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mRvRank.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRankAdapter = new RankChildAdapter(getActivity());
        mRvRank.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.app_divider)));
        mTvScore.setText(FormatString.newRankMine(getString(R.string.rank_mine_none)));
        call();
        load();
        setupOnRefreshListener();
    }

    private void setupOnRefreshListener() {
        mRvRank.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                call();
            }
        });
    }

    private void call(){
        mIsRefresh = false;
        call(new JobGetRankList(mRank.getType()), new SimpleCallback<BaseListEntry<RankEntity>>() {

            @Override
            public void onSuccess(BaseListEntry<RankEntity> data) {
                try{
                    mTotalCount = data.getBaseListData().getTotalCount();
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

    private void setAdapter(){
        if(null == mRvRank.getAdapter() && mTotalCount <= 0){
            mRvRank.setAdapter(mRankAdapter);
        }
        mRankAdapter.notifyDataSetChanged();
    }

    private void load(){
        loader(RankEntity.class, Where.rank(mRank.getType()), new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                String uid = UserProvider.getInstance().getUid();
                List<RankEntity> rankEntityList = SqlConvert.convertAll(cursor, RankEntity.class);
                for(int i = 0; null != rankEntityList && i< rankEntityList.size(); i++){
                    if(rankEntityList.get(i).getUid().equals(uid)){
                        mTvScore.setText(FormatString.newRankMine(String.valueOf( i + 1 )));
                        break;
                    }
                }
                if(null == mRvRank.getAdapter() && (mIsRefresh || ( null != rankEntityList && rankEntityList.size() > 0))){
                    mRvRank.setAdapter(mRankAdapter);
                }
                mRankAdapter.notifyDataSetChanged(rankEntityList);

            }
        });
    }
}
