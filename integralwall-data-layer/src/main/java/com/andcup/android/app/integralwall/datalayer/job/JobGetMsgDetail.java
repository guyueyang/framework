package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.MsgEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.database.activeandroid.query.Update;
import com.andcup.android.frame.datalayer.exception.JobException;
import com.andcup.android.frame.datalayer.sql.action.SqlInsert;

import java.io.IOException;
import java.util.List;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/25.
 */
public class JobGetMsgDetail extends IntegralWallJob<BaseEntity<MsgEntity>> {

    String mMsgId;

    public JobGetMsgDetail(String msgId){
        mMsgId = msgId;
    }

    @Override
    public BaseEntity<MsgEntity> start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long time = getTimestamp();
        String sign = md5(uid + mMsgId + time + getKey());
        return apis().getMsgDetail(uid, mMsgId, time, sign).execute().body();
    }

    @Override
    public void finish(BaseEntity<MsgEntity> msgEntityBaseListEntry) throws JobException {
        super.finish(msgEntityBaseListEntry);
        new Update(MsgEntity.class)
                .set(DbColumn.READED + "=" + MsgEntity.STATE_READ_YES)
                .where(DbColumn.MSG_ID + "=" +  mMsgId)
                .execute();
    }
}
