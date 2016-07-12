package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.FamilyInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.frame.datalayer.sql.SqlAction;
import com.andcup.android.frame.datalayer.sql.action.SqlInsert;
import com.andcup.android.frame.datalayer.exception.JobException;

import java.io.IOException;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/14.
 */
public class JobGetFamilyInfo extends IntegralWallJob<BaseEntity<FamilyInfoEntity>>{

    @Override
    public BaseEntity<FamilyInfoEntity> start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long  time = getTimestamp();
        String sign = md5(uid + time + getKey());
        return apis().getFamilyInfo(uid, time, sign ).execute().body();
    }

    @Override
    public void finish(BaseEntity<FamilyInfoEntity> familyInfo) throws JobException {
        familyInfo.body().setUid(UserProvider.getInstance().getUid());
        SqlInsert<FamilyInfoEntity> dataOperator = new SqlInsert<>(FamilyInfoEntity.class, Where.user());
        dataOperator.insert(familyInfo.body());
    }
}
