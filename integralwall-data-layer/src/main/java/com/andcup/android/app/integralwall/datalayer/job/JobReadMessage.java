package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.MsgEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskBusinessEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.database.activeandroid.query.Update;
import com.andcup.android.frame.datalayer.sql.SqlAction;
import com.andcup.android.frame.datalayer.sql.action.SqlInsert;
import com.andcup.android.frame.datalayer.exception.JobException;


import java.io.IOException;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/25.
 */
public class JobReadMessage extends IntegralWallJob<BaseEntity> {

    MsgEntity mMsgEntity;

    public JobReadMessage(MsgEntity msgEntity){
        mMsgEntity = msgEntity;
    }

    @Override
    public BaseEntity start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long time = getTimestamp();
        String sign = md5(uid + mMsgEntity.getMsgId() + time + getKey());
        updateMsgEntity();
        return apis().readMsg(uid, mMsgEntity.getMsgId(), time, sign).execute().body();
    }

    private void updateMsgEntity(){
        new Update(MsgEntity.class)
                .set(DbColumn.READED + "=" + MsgEntity.STATE_READ_YES)
                .where(DbColumn.MSG_ID + "=" +  mMsgEntity.getMsgId())
                .execute();
    }
}
