package com.andcup.android.app.integralwall.view.guide;

import android.os.Bundle;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobGetUserInfo;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.yl.android.cpa.R;

/**
 * Created by Administrator on 2016/3/26.
 */
public class TaskGuideFragment extends BaseFragment {

    int step;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first_task;
    }
    @Override
    protected void afterActivityCreate(Bundle afterActivityCreate){
        super.afterActivityCreate(afterActivityCreate);
        changeDailyFirst();
        call();
    }

    private void call(){
        call(new JobGetUserInfo(), new SimpleCallback<BaseEntity<UserInfoEntity>>() {
            @Override
            public void onSuccess(BaseEntity<UserInfoEntity> userInfoEntityBaseEntity) {
                changeDailyFirst();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        call();
    }
    @Override
    public void onEvent(Object object) {
        super.onEvent(object);
        call();
    }

    public void changeDailyFirst(){
        step= UserProvider.getInstance().getFirstLandingStep();
        if(step>= UserInfoEntity.STEP_TASK_DONE){
            setTitle(R.string.daily_landing);
            go(DailyTaskFragment.class, R.id.fl_content);
        }else {
            setTitle(R.string.first_landing);
            go(NewUserTaskFragment.class, R.id.fl_content);
        }
    }
}
