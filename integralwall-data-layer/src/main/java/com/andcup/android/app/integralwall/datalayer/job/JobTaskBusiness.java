package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.CompleteInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskBusinessEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;

import java.io.IOException;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/18.
 */
public class JobTaskBusiness extends IntegralWallJob<BaseEntity<CompleteInfoEntity>> {

    TaskBusinessEntity mBusinessEntity;

    public JobTaskBusiness(TaskBusinessEntity taskBusinessEntity){
        mBusinessEntity = taskBusinessEntity;
    }

    @Override
    public BaseEntity<CompleteInfoEntity> start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long   time= getTimestamp();
        String sign= md5(uid+ mBusinessEntity.getTaskId() + time + getKey());
        return apis().completeTask(uid,
                mBusinessEntity.getTaskId(),
                mBusinessEntity.getTaskOptionId(),
                null,
                mBusinessEntity.getMark(),
                String.valueOf(mBusinessEntity.getTimeStart()/1000),
                String.valueOf(mBusinessEntity.getTimeEnd()/1000),
                time,
                sign,
                null).execute().body();
    }
}
