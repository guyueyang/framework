package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.MyTaskQuickEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.frame.datalayer.sql.SqlAction;
import com.andcup.android.frame.datalayer.sql.action.SqlInsert;
import com.andcup.android.frame.datalayer.exception.JobException;

import java.io.IOException;
import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/16.
 */
public class JobGetMyQuickTasks extends IntegralWallJob<BaseListEntry<MyTaskQuickEntity>> {

    int QUICK_TYPE     = 2;
    int QUICK_STATE_MY = 3;

    int mPageIndex;
    int mPageSize;

    public JobGetMyQuickTasks(int pageIndex, int pageSize){
        mPageIndex = pageIndex;
        mPageSize  = pageSize;
    }

    @Override
    public BaseListEntry<MyTaskQuickEntity> start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long  time = getTimestamp();
        String sign = md5(uid + 0 + time + getKey());
        return apis().getMyQuickTasks(uid, 0, QUICK_TYPE, QUICK_STATE_MY, mPageIndex + 1, mPageSize, time, sign ).execute().body();
    }

    @Override
    public void finish(BaseListEntry<MyTaskQuickEntity> tasks) throws JobException {
        List<MyTaskQuickEntity> taskEntityList = tasks.getBaseListData().getList();
        for (int i = 0; null != taskEntityList && i < taskEntityList.size(); i++) {
            MyTaskQuickEntity task = taskEntityList.get(i);
            task.setTaskState(QUICK_TYPE);
            task.setUid(UserProvider.getInstance().getUid());
        }
        SqlInsert<MyTaskQuickEntity> dataOperator = new SqlInsert<>(MyTaskQuickEntity.class, Where.user());
        dataOperator.insert(tasks.getBaseListData().getList(), mPageIndex * mPageSize);
    }
}
