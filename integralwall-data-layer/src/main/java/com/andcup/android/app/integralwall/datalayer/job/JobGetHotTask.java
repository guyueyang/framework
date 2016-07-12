package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.HotTaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.frame.datalayer.sql.SqlAction;
import com.andcup.android.frame.datalayer.sql.action.SqlInsert;
import com.andcup.android.frame.datalayer.exception.JobException;

import java.io.IOException;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/15.
 */
public class JobGetHotTask extends IntegralWallJob<BaseListEntry<HotTaskEntity>> {

    @Override
    public BaseListEntry<HotTaskEntity> start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        int number = 10;
        long time= getTimestamp();
        String sign = md5(uid + number + time + getKey());
        BaseListEntry<HotTaskEntity> entry = apis().getHotTask(uid,number,time,sign).execute().body();
        return entry;
    }

    @Override
    public void finish(BaseListEntry<HotTaskEntity> data) throws JobException {
        super.finish(data);
        SqlInsert<HotTaskEntity> dataOperator = new SqlInsert<>(HotTaskEntity.class);
        dataOperator.insert(data.data().getList(), 0);
    }
}
