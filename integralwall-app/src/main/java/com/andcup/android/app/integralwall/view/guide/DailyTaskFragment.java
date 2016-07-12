package com.andcup.android.app.integralwall.view.guide;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobDailyTask;
import com.andcup.android.app.integralwall.datalayer.job.JobDailyTaskAddScore;
import com.andcup.android.app.integralwall.datalayer.model.DailyTaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.event.ShareEvent;
import com.andcup.android.app.integralwall.tools.ShareHelper;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.home.sign.CalendarFragment;
import com.andcup.android.app.integralwall.view.share.ShareContent;
import com.andcup.android.app.integralwall.view.task.feature.FeatureTaskFragment;
import com.andcup.android.app.integralwall.view.task.union.UnionTaskFragment;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/26.
 */
public class DailyTaskFragment extends BaseFragment{
    @Bind(R.id.lv_daily_landing)
    ListView mLvDailyLanding;
    @Bind(R.id.ll_loading)
    LinearLayout mLlLoading;
    DailyTaskAdapter        mDailyTaskAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_daily_task;
    }
    @Override
    protected void afterActivityCreate(Bundle afterActivityCreate){
        super.afterActivityCreate(afterActivityCreate);
        mDailyTaskAdapter=new DailyTaskAdapter(getActivity());
        mLvDailyLanding.setAdapter(mDailyTaskAdapter);
        mDailyTaskAdapter.DailyTaskFragment();
        load();
    }

    @Override
    public void onResume() {
        super.onResume();
        call();
    }

    @Subscribe
    public void onEventShare(ShareEvent shareEvent) {
        call();
    }

    public void call(){
        call(new JobDailyTask(), new SimpleCallback<BaseListEntry<DailyTaskEntity>>());
    }

    public void load(){
        loader(DailyTaskEntity.class, (SqlLoader.CursorCallBack) new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                dailyTask(cursor);
            }
        });
    }

    private void dailyTask(Cursor cursor){
        mLlLoading.setVisibility(View.GONE);
        List<DailyTaskEntity> dailyTaskEntities = new ArrayList<>();
        List<DailyTaskEntity> dailyTask= SqlConvert.convertAll(cursor, DailyTaskEntity.class);
        for(int i = 0;null != dailyTask && i < dailyTask.size(); i++){
            dailyTaskEntities.add(dailyTask.get(i));
        }
        mDailyTaskAdapter.notifyDataSetChanged(dailyTaskEntities);
    }


    @OnClick(R.id.btn_complete_task)
    public void  completeTask(){
        call(new JobDailyTaskAddScore(), new SimpleCallback<BaseEntity>() {
            @Override
            public void onError(Throwable e) {
                SnackBar.make(getActivity(), e.getMessage()).show();
            }

            @Override
            public void onSuccess(BaseEntity baseEntity) {
                SnackBar.make(getActivity(), baseEntity.getMessage()).show();
            }
        });
    }

    @Subscribe
    public void toDailyCompleteTask(DailyTaskEntity dailyTaskEntity){
        String position = dailyTaskEntity.getItemId();
        int id=Integer.parseInt(position);
        if(id==DailyTaskEntity.DAILY_SIGN_IN){
            go2(CalendarFragment.class,null);
        }else if(id==DailyTaskEntity.DAILY_SHARE){
            ShareContent shareContent=new ShareContent(null,null,null,"1");
            ShareHelper.share(getChildFragmentManager(), shareContent);
        }else if(id==DailyTaskEntity.DAILY_QUICK_TASK){
            Bundle bundle = new Bundle();
            bundle.putString(BundleKey.IS_UNION,"1");
            go2(FeatureTaskFragment.class,bundle);
        }else if(id==DailyTaskEntity.DAILY_UNION_TASK){
            Bundle bundle = new Bundle();
            bundle.putString(BundleKey.IS_UNION,"1");
            go2(UnionTaskFragment.class,bundle);
        }
    }
}
