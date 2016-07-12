package com.andcup.android.app.integralwall.view.family.member;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.FamilyInfoEntity;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.integralwall.commons.widget.VerticalMarqueeTextView;
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
public class DynamicFragment extends BaseFragment{

    @Bind(R.id.srv_refresh)
    RecyclerView mSrvDynamic;
    @Bind(R.id.list_item_empty)
    View mListItemEmpty;

    DynamicAdapter    mAdapter;

    int mCount = 0;
    final int INTERVAL = 50;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mAdapter = new DynamicAdapter(getActivity());
        mSrvDynamic.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSrvDynamic.setEnabled(false);
        mSrvDynamic.setVerticalScrollBarEnabled(false);
        mSrvDynamic.setHorizontalScrollBarEnabled(false);
        mSrvDynamic.setAdapter(mAdapter);
        load();
    }

    private void load(){
        loader(FamilyInfoEntity.class, Where.user(), new SqlLoader.CallBack<FamilyInfoEntity>() {
            @Override
            public void onUpdate(FamilyInfoEntity familyInfoEntity) {
                if( null != familyInfoEntity){
                    mAdapter.notifyDataSetChanged(familyInfoEntity.getFamilyDynamic());
                    try{
                        mCount = familyInfoEntity.getFamilyDynamic().size();
                    }catch (Exception e){

                    }
                    mListItemEmpty.setVisibility(mCount <= 0 ? View.VISIBLE : View.GONE);
                    mHandler.sendEmptyMessageDelayed(0, INTERVAL);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeMessages(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.sendEmptyMessageDelayed(0, INTERVAL);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mCount <= 1){
                return;
            }
            mSrvDynamic.scrollBy(0, 1);
            mHandler.sendEmptyMessageDelayed(0, INTERVAL);
        }
    };
}
