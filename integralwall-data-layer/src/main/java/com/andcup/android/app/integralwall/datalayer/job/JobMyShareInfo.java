package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.ShareInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.frame.datalayer.sql.SqlAction;
import com.andcup.android.frame.datalayer.sql.action.SqlInsert;
import com.andcup.android.frame.datalayer.exception.JobException;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/25.
 */
public class JobMyShareInfo extends IntegralWallJob<BaseEntity<ShareInfoEntity>> {

    @Override
    public BaseEntity<ShareInfoEntity> start() throws IOException {
        String uid= UserProvider.getInstance().getUid();
        Long   time=getTimestamp();
        String sign=md5(uid+time+getKey());
        return apis().getMyShareInfo(uid,time,sign).execute().body();
    }

    @Override
    public void finish(BaseEntity<ShareInfoEntity> shareInfoEntity) throws JobException{
        SqlInsert<ShareInfoEntity> dataOperator = new SqlInsert<>(ShareInfoEntity.class, Where.user());
        shareInfoEntity.body().setUid(UserProvider.getInstance().getUid());
        dataOperator.insert(shareInfoEntity.body());
    }
}
