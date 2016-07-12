package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.FirstLandingTaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskUnionPlatformEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.frame.datalayer.sql.SqlAction;
import com.andcup.android.frame.datalayer.sql.action.SqlInsert;
import com.andcup.android.frame.datalayer.exception.JobException;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/22.
 */
public class JobFirstLandingTask extends IntegralWallJob<BaseListEntry<FirstLandingTaskEntity>> {
    @Override
    public BaseListEntry<FirstLandingTaskEntity> start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long   time= getTimestamp();
        String sign=md5(uid+time+getKey());
        return apis().getFirstLandingTask(uid,time,sign)
                .execute()
                .body();
    }
    @Override
    public void finish(BaseListEntry<FirstLandingTaskEntity> firstLandingTaskEntityBaseListEntry) throws JobException{
        SqlInsert<FirstLandingTaskEntity> dataOperator = new SqlInsert<>(FirstLandingTaskEntity.class);
        dataOperator.insert(firstLandingTaskEntityBaseListEntry.getBaseListData().getList(), 0);
    }
}
