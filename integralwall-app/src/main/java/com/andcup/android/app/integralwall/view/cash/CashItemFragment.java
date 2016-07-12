package com.andcup.android.app.integralwall.view.cash;

import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobGetCashingItemList;
import com.andcup.android.app.integralwall.datalayer.job.JobGetUserInfo;
import com.andcup.android.app.integralwall.datalayer.model.CashingItemEntity;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.event.ScoreEvent;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.frame.view.annotations.Restore;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/22.
 */
public class CashItemFragment extends BaseFragment {

    @Restore(BundleKey.CASH_ITEM)
    CashItem mCashItem;
    @Bind(R.id.rv_cashing_item)
    RecyclerView mGvCashItem;
    @Bind(R.id.iv_banner)
    ImageView mIvBanner;
    @Bind(R.id.tv_score)
    TextView mTvScore;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    CashItemAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cashing_item;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        setTitle(mCashItem.mTitle);
        setBanner();
        mAdapter = new CashItemAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mGvCashItem.setLayoutManager(manager);
        mGvCashItem.setAdapter(mAdapter);
        load();
        callCashingItem();
        go(mCashItem.mClazz, R.id.fr_container);
        monitorUserInfo();
        if (mCashItem == CashItem.PHONEFARE) {
            mTvTitle.setText(getString(R.string.phone_fare_value));
        }
    }

    private void setBanner() {
        Point point = getWindowSize();
        ViewGroup.LayoutParams lp = mIvBanner.getLayoutParams();
        lp.height = (int) (point.x / 2.4);
        mIvBanner.setLayoutParams(lp);
        mIvBanner.setBackgroundDrawable(getResources().getDrawable(mCashItem.mBannerId));
    }

    private void callCashingItem() {
        call(new JobGetCashingItemList(mCashItem.mType), new SimpleCallback<BaseListEntry<CashingItemEntity>>());
    }

    private void load() {
        loader(CashingItemEntity.class, Where.cashingItem(mCashItem.mType), new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                List<CashingItemEntity> items = SqlConvert.convertAll(cursor, CashingItemEntity.class);
                mAdapter.notifyDataSetChanged(items);
            }
        });
    }

    @Subscribe
    public void refreshUserInfo(ScoreEvent scoreEvent){
        call(new JobGetUserInfo(), new CallBack<BaseEntity<UserInfoEntity>>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess(BaseEntity<UserInfoEntity> userInfoEntityBaseEntity) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void monitorUserInfo(){
        loader(UserInfoEntity.class, new SqlLoader.CallBack<UserInfoEntity>() {
            @Override
            public void onUpdate(UserInfoEntity userInfoEntity) {
                if (null == userInfoEntity) {
                    return;
                }
                mTvScore.setText(FormatString.newCashing(userInfoEntity.getScore()));
            }
        });
    }
}
