package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.MsgEntity;
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
 * Created by Amos on 2016/3/25.
 */
public class JobGetMsgList extends IntegralWallJob<BaseListEntry<MsgEntity>> {

    int mPageIndex;
    int mPageSize;
    int mReaded;

    public JobGetMsgList(int pageIndex, int pageSize, int readed){
        mPageIndex = pageIndex;
        mPageSize  = pageSize;
        mReaded    = readed;
    }

    @Override
    public BaseListEntry<MsgEntity> start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long time = getTimestamp();
        String sign = md5(uid + mPageSize + time + getKey());
        return apis().getMsgList(uid, mPageIndex + 1, mPageSize, mReaded, time, sign).execute().body();
    }

    @Override
    public void finish(BaseListEntry<MsgEntity> msgEntityBaseListEntry) throws JobException {
        super.finish(msgEntityBaseListEntry);
        SqlInsert<MsgEntity> msgSql = new SqlInsert<MsgEntity>(MsgEntity.class, Where.readed(mReaded));
        List<MsgEntity> msgEntities = null;
        if( null != msgEntityBaseListEntry){
            msgEntities = msgEntityBaseListEntry.data().getList();
            for( int i = 0; null != msgEntities && i< msgEntities.size(); i++){
                msgEntities.get(i).setUid(UserProvider.getInstance().getUid());
                if(msgEntities.get(i).getStatus() > 0){
                    msgEntities.get(i).setReaded();
                }
            }
        }
        msgSql.insert(msgEntities, mPageIndex * mPageSize);
    }
}
