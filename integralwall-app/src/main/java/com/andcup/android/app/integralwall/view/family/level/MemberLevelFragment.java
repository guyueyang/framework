package com.andcup.android.app.integralwall.view.family.level;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.EditText;
import android.widget.TextView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobGetFamilyMember;
import com.andcup.android.app.integralwall.datalayer.model.FamilyMemberEntity;
import com.andcup.android.app.integralwall.datalayer.model.MemberEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.family.member.MemberFragment;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.RecycleViewDivider;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.yl.android.cpa.R;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/23.
 */
public class MemberLevelFragment extends BaseFragment {

    @Bind(R.id.tv_family_number)
    TextView mTvFamilyNumber;
    @Bind(R.id.tv_family_score)
    TextView mTvFamilyScore;
    @Restore(BundleKey.LEVEL)
    String mLevel;
    @Restore(BundleKey.LEVEL_MEMBERS)
    String mMembers;
    @Restore(BundleKey.LEVEL_SCORE)
    String mScore;
    @Bind(R.id.srv_refresh)
    SuperRecyclerView  mSrvRefresh;
    @Bind(R.id.et_input)
    EditText mEtInput;
    MemberLevelAdapter mAdapter;

    int mPageIndex;
    int mPageSize=20;
    int mTotalCount;
    String mKeyWord = "";
    int mLoaderId = genLoaderId();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_member_list;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        setTitle(mLevel.equals(String.valueOf(MemberFragment.LEVEL_1)) ? R.string.family_title_1 : R.string.family_title_2);
        mTvFamilyNumber.setText(getString(R.string.family_member, mMembers));
        mTvFamilyScore.setText(getString(R.string.family_total_in, mScore));

        mSrvRefresh.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MemberLevelAdapter(getActivity());
        mSrvRefresh.setAdapter(mAdapter);
        mSrvRefresh.showProgress();
        mSrvRefresh.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.app_divider)));
        //int recycle view.
        setupOnRefreshListener();
        setupOnRefreshMoreListener();
        //load from remote
        call(mPageIndex, mPageSize, mKeyWord);
        //load from lo
        load();
    }

    private void call(int pageIndex, int pageSize, String keyword){
        call(new JobGetFamilyMember(pageIndex, pageSize, Integer.parseInt(mLevel), keyword), new SimpleCallback<BaseEntity<FamilyMemberEntity>>() {
            @Override
            public void onError(Throwable e) {
                mSrvRefresh.showRecycler();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSuccess(BaseEntity<FamilyMemberEntity> quickTaskEntityBaseListEntry) {
                try{
                    mTotalCount = quickTaskEntityBaseListEntry.body().getLevelTotal();
                }catch (Exception e){

                }
                mSrvRefresh.showRecycler();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick(R.id.btn_search)
    public void onSearch(){
        String text = mEtInput.getText().toString();
        if(text.equals(mKeyWord)){
            return;
        }
        mKeyWord    = text;
        mTotalCount = 0;
        mSrvRefresh.showProgress();
        call(mPageIndex, mPageSize, mKeyWord);
        load();
    }

    private void load(){
        loader(mLoaderId, MemberEntity.class, Where.memberLevel(UserProvider.getInstance().getUid(), Integer.valueOf(mLevel), mKeyWord), new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                List<MemberEntity> memberEntities = SqlConvert.convertAll(cursor, MemberEntity.class);
                showRecycler(memberEntities);
                mAdapter.notifyDataSetChanged(memberEntities);
            }
        });
    }

    private void showRecycler(List<MemberEntity> memberEntities){
        if((null != memberEntities && memberEntities.size() > 0)){
            mSrvRefresh.showRecycler();
        }
    }

    private void setupOnRefreshListener() {
        mSrvRefresh.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex = 0;
                call(mPageIndex, mPageSize, mKeyWord);
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
                    call(mPageIndex, mPageSize, mKeyWord);
                }else{
                    mSrvRefresh.hideMoreProgress();
                }
            }
        });
    }
}
