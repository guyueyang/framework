package com.andcup.android.app.integralwall.datalayer.job;
import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.HotTaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskQuickEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskUnionPlatformEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.frame.datalayer.sql.SqlAction;
import com.andcup.android.frame.datalayer.sql.action.SqlInsert;
import com.andcup.android.frame.datalayer.exception.JobException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public class JobUnionPlatform extends IntegralWallJob<BaseListEntry<TaskUnionPlatformEntity>> {

    int mPageIndex;
    int mPageSize;

    public JobUnionPlatform(int pageIndex,int pageSize){
        mPageIndex=pageIndex;
        mPageSize=pageSize;
    }
    @Override
    public BaseListEntry<TaskUnionPlatformEntity> start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long time=getTimestamp();
        String sign=md5(uid + time + getKey());
        return apis().getUnionPlatform(uid,mPageIndex+1,mPageSize,time,sign)
                .execute()
                .body();
    }

    @Override
    public void finish(BaseListEntry<TaskUnionPlatformEntity> tasks) throws JobException {
        SqlInsert<TaskUnionPlatformEntity> dataOperator = new SqlInsert<>(TaskUnionPlatformEntity.class);
        dataOperator.insert(tasks.getBaseListData().getList(), mPageIndex * mPageSize);
    }
}
