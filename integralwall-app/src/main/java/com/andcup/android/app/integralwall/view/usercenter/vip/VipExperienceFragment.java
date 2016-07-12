package com.andcup.android.app.integralwall.view.usercenter.vip;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.IntegralWallDataLayer;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobExperience;
import com.andcup.android.app.integralwall.datalayer.model.ExperienceEntity;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.tools.LinkViewHelper;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.integralwall.commons.RecycleViewDivider;
import com.andcup.android.integralwall.commons.widget.URIRoundedImageView;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.yl.android.cpa.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/25.
 */
public class VipExperienceFragment extends BaseFragment {

    final int mPageSize = 10;
    int mPageIndex;
    int mTotalCount;
    @Bind(R.id.tv_nick_name)
    TextView mTvNickName;
    @Bind(R.id.tv_incoming)
    TextView mTvIncoming;
    @Bind(R.id.tv_user_id)
    TextView mTvUserId;
    @Bind(R.id.riv_avatar)
    URIRoundedImageView mRivAvatar;
    @Bind(R.id.tv_pre_level)
    TextView          mTvPreLevel;
    @Bind(R.id.tv_next_level)
    TextView          mTvNextLevel;
    @Bind(R.id.pb_level)
    ProgressBar mPbLevel;
    UserInfoEntity mUserInfoEntity;
    @Bind(R.id.srv_refresh)
    SuperRecyclerView mSrvRefresh;
    @Bind(R.id.sdv_vip)
    ImageView mBtnVip;
    @Bind(R.id.tv_level)
    TextView mTvLevel;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    VipExperienceAdapter mAdapter;

    SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    boolean mIsRefresh = false;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_member_detail;
    }

    @Override
    protected void afterActivityCreate(Bundle afterActivityCreate){
        super.afterActivityCreate(afterActivityCreate);
        mTvTitle.setText(R.string.member_vip);
        mSrvRefresh.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new VipExperienceAdapter(getActivity());
        mSrvRefresh.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.app_divider)));
        //int recycle view.
        monitorUserInfo();
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

    public void call(int pageIndex,int pageSize){
        call(new JobExperience(pageIndex, pageSize), new SimpleCallback<BaseListEntry<ExperienceEntity>>() {
            @Override
            public void onError(Throwable e) {
                mIsRefresh = true;
                setAdapter();
            }

            @Override
            public void onSuccess(BaseListEntry<ExperienceEntity> experienceEntityBaseListEntry) {
                try {
                    mTotalCount = experienceEntityBaseListEntry.data().getTotalCount();
                } catch (Exception e) {

                }
                mIsRefresh = true;
                setAdapter();
            }
        });
    }

    public void load(){
        loader(ExperienceEntity.class, Where.user(), new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                notifyDataSetChanged(cursor);
            }
        });
    }

    private void notifyDataSetChanged(Cursor cursor) {
        List<VipExperienceItem> memberDetailItems = new ArrayList<>();
        List<ExperienceEntity> tasks = SqlConvert.convertAll(cursor, ExperienceEntity.class);
        for (int i = 0; null != tasks && i < tasks.size(); i++) {
            VipExperienceItem<ExperienceEntity> task = new VipExperienceItem<>(tasks.get(i));
            memberDetailItems.add(task);
        }
        setUpAdapter(tasks);
        mAdapter.notifyDataSetChanged(memberDetailItems);
    }

    private void setUpAdapter(List<ExperienceEntity> tasks){
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

    @OnClick(R.id.tv_level_that)
    protected void onSdvVip(){
        LinkViewHelper.open(getActivity(), IntegralWallDataLayer.getInstance().getPlatformConfigure().getPlatformUrl() + getString(R.string.vip_address), getString(R.string.level_that));
    }

    @OnClick(R.id.iv_back)
    public void onBack(){
        getActivity().finish();
    }

    public void LoadingVIP(String vipLevel){
        int level= Integer.parseInt(vipLevel);
        mBtnVip.setImageLevel(level);

    }

    private void monitorUserInfo(){
        loader(UserInfoEntity.class, Where.user(), new SqlLoader.CallBack<UserInfoEntity>() {
            @Override
            public void onUpdate(UserInfoEntity userInfoEntity) {
                if (null == userInfoEntity) {
                    return;
                }
                UserProvider.getInstance().setUserInfo(userInfoEntity);
                // set user info.
                mUserInfoEntity = userInfoEntity;
                if(!TextUtils.isEmpty(mUserInfoEntity.getAvatar())){
                    mRivAvatar.setImageURI(Uri.parse(mUserInfoEntity.getAvatar()));
                }
                mTvUserId.setText(UserProvider.getInstance().getUid());
                Date date = new Date(Long.valueOf(mUserInfoEntity.getRegisterTime())* 1000);
                mTvIncoming.setText(mDateFormat.format(date));
                mTvNickName.setText(mUserInfoEntity.getNickName());
                mTvPreLevel.setText("VIP"+mUserInfoEntity.getLevel());
                mTvNextLevel.setText(mUserInfoEntity.getNextLevel());
                mTvLevel.setText(mUserInfoEntity.getExperience()+"/"+mUserInfoEntity.getNextLevelExperience());

                try{
                    float progress = Float.parseFloat(mUserInfoEntity.getUpdateProgress());
                    mPbLevel.setMax(100);
                    mPbLevel.setProgress((int) (progress * 100));
                }catch (Exception e){

                }
                LoadingVIP(mUserInfoEntity.getLevel());
            }
        });
    }
}
