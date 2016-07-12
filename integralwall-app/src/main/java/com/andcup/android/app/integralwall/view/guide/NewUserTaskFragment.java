package com.andcup.android.app.integralwall.view.guide;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobFirstLandingScore;
import com.andcup.android.app.integralwall.datalayer.job.JobFirstLandingTask;
import com.andcup.android.app.integralwall.datalayer.job.JobIsQbCanCheck;
import com.andcup.android.app.integralwall.datalayer.model.CheckQbEntity;
import com.andcup.android.app.integralwall.datalayer.model.FirstLandingTaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.event.QBEvent;
import com.andcup.android.app.integralwall.event.ShareEvent;
import com.andcup.android.app.integralwall.tools.Dialog;
import com.andcup.android.app.integralwall.tools.ShareHelper;
import com.andcup.android.app.integralwall.tools.TaskHelper;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.cash.qb.DoTaskFragment;
import com.andcup.android.app.integralwall.view.cash.qb.QBFragment;
import com.andcup.android.app.integralwall.view.dialog.BindPhoneNumberFragment;
import com.andcup.android.app.integralwall.view.dialog.DisabledExchangeFragment;
import com.andcup.android.app.integralwall.view.home.sign.CalendarFragment;
import com.andcup.android.app.integralwall.view.share.ShareContent;
import com.andcup.android.app.integralwall.view.usercenter.edit.EditFragment;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/21.
 */
public class NewUserTaskFragment extends BaseFragment {

    @Bind(R.id.lv_first_landing)
    ListView mLvFirstLanding;
    @Bind(R.id.ll_loading)
    LinearLayout mLlLoading;
    NewUserTaskAdapter mAdapter;
    boolean mIsRefresh = false;
    int mLoaderId = genLoaderId();

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mAdapter = new NewUserTaskAdapter(getActivity());
        mLvFirstLanding.setAdapter(mAdapter);
        setTitle(R.string.first_landing);
        load();
        call();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first_landing;
    }

    @Override
    public void onResume() {
        super.onResume();
        call();
    }


    public void call() {
        call(new JobFirstLandingTask(), new SimpleCallback<BaseListEntry<FirstLandingTaskEntity>>() {
            @Override
            public void onError(Throwable e) {
                mIsRefresh = true;
                setAdapter();
            }
        });
    }

    public void load() {
        loader(FirstLandingTaskEntity.class, (SqlLoader.CursorCallBack) new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                firstLanding(cursor);
            }
        });
    }

    private void firstLanding(Cursor cursor) {
        List<FirstLandingTaskEntity> unionTaskItems = new ArrayList<>();
        List<FirstLandingTaskEntity> tasks = SqlConvert.convertAll(cursor, FirstLandingTaskEntity.class);
        for (int i = 0; null != tasks && i < tasks.size(); i++) {
            unionTaskItems.add(tasks.get(i));
        }
        setUpAdapter(tasks);
        mAdapter.notifyDataSetChanged(unionTaskItems);
    }

    private void setUpAdapter(List<FirstLandingTaskEntity> tasks) {
        mLlLoading.setVisibility(View.GONE);
        if (mLvFirstLanding.getAdapter() == null && (mIsRefresh || (null != tasks && tasks.size() > 0))) {
            mLvFirstLanding.setAdapter(mAdapter);
        }
    }

    private void setAdapter() {
        mLlLoading.setVisibility(View.GONE);
        if (mLvFirstLanding.getAdapter() == null) {
            mLvFirstLanding.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void toFirstCompleteTask(FirstLandingTaskEntity firstLandingTaskEntity) {
        int id = Integer.parseInt(firstLandingTaskEntity.getmId());
        if (id == FirstLandingTaskEntity.FIRST_RECEIVED_SCORE) {
            //receivedScore();
            go2(NewAboutFragment.class, null);
        }
        if (id == FirstLandingTaskEntity.FIRST_PERSONEL_DATA) {
            go2(EditFragment.class, null);
        } else if (id == FirstLandingTaskEntity.FIRST_QUICK_TASK) {
            TaskHelper.openTask(Integer.parseInt(firstLandingTaskEntity.getTaskId()));
        } else if (id == FirstLandingTaskEntity.FIRST_UNION_TASK) {
            go2(CalendarFragment.class, null);
        } else if (id == FirstLandingTaskEntity.FIRST_SHARE) {
            ShareContent shareContent = new ShareContent(null, null, null, "1");
            ShareHelper.share(getChildFragmentManager(), shareContent);
        } else if (id == FirstLandingTaskEntity.FIRST_QB_EXCHANGE) {
            if (!bindPhone()) {
                return;
            }
            if (prohibited()) {
                return;
            }
            showLoading();
            callQb();

        }

    }

    protected boolean bindPhone() {
        if (TextUtils.isEmpty(UserProvider.getInstance().getUserInfo().getMobile())) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(BundleKey.CONTENT, getString(R.string.bind_phone_number));
            Dialog.BIND_PHONE
                    .build(BindPhoneNumberFragment.class, bundle)
                    .okText(getString(R.string.to_bind_phone_number))
                    .show(getChildFragmentManager());
            return false;
        }
        return true;
    }

    protected boolean prohibited() {
        try {
            boolean isDisabled = UserProvider.getInstance().getUserInfo().isDisableExchange();
            if (isDisabled) {
                Dialog.EXCHANGE_DISABLED.build(DisabledExchangeFragment.class, null).show(getChildFragmentManager());
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private void callQb() {
        call(new JobIsQbCanCheck(), new SimpleCallback<CheckQbEntity>() {
            @Override
            public void onSuccess(CheckQbEntity jobEntity) {
                if (null != jobEntity) {
                    int stateCode = jobEntity.getErrCode();
                    if (stateCode == CheckQbEntity.QB_EXCHANGE_READY) {
                        Dialog.QB.build(QBFragment.class, null).show(getChildFragmentManager());
                        //
                    } else {
                        Dialog.QB_TASK.build(DoTaskFragment.class, null).show(getChildFragmentManager());
                        //Dialog.QB.build(QBFragment.class, null).show(getChildFragmentManager());
                    }
                }
                hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                hideLoading();
                SnackBar.make(getContext(), e.getMessage()).show();
            }
        });
    }

    @Override
    public void onEvent(Object object) {
        super.onEvent(object);
        if (object instanceof ShareEvent) {
            call();
        }
        if (object instanceof QBEvent) {
            call();
        }
    }
}
