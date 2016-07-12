package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.DailyTaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.frame.datalayer.sql.SqlAction;
import com.andcup.android.frame.datalayer.sql.action.SqlInsert;
import com.andcup.android.frame.datalayer.exception.JobException;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/22.
 */
public class JobDailyTask extends IntegralWallJob<BaseListEntry<DailyTaskEntity>> {
    @Override
    public BaseListEntry<DailyTaskEntity> start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long   time= getTimestamp();
        String sign=md5(uid+time+getKey());
        return apis().getDailyTask(uid, time, sign)
                .execute()
                .body();
    }
    @Override
    public void finish(BaseListEntry<DailyTaskEntity> dailyTaskEntityBaseListEntry) throws JobException{
        SqlInsert<DailyTaskEntity> dataOperator = new SqlInsert<>(DailyTaskEntity.class);
        dataOperator.insert(dailyTaskEntityBaseListEntry.getBaseListData().getList(), 0);
    }
}
