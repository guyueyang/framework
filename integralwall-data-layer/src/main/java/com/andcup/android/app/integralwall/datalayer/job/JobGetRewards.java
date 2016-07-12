package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.RewardEntity;
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
 * Created by Amos on 2016/3/24.
 */
public class JobGetRewards extends IntegralWallJob<BaseListEntry<RewardEntity>>{

    @Override
    public BaseListEntry<RewardEntity> start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long time  = getTimestamp();
        String sign= md5(uid + time + getKey());
        return apis().getRewards(uid, time, sign).execute().body();
    }

    @Override
    public void finish(BaseListEntry<RewardEntity> rewardEntityBaseListEntry) throws JobException {
        super.finish(rewardEntityBaseListEntry);
        if( null == rewardEntityBaseListEntry){
            return;
        }
        String uid = UserProvider.getInstance().getUid();
        List<RewardEntity> list = rewardEntityBaseListEntry.getBaseListData().getList();
        for( int i = 0; null != list && i< list.size(); i++){
            list.get(i).setUserId(uid);
            list.get(i).setRule(rewardEntityBaseListEntry.data().getContent());
        }
        SqlInsert<RewardEntity> sql = new SqlInsert<RewardEntity>(RewardEntity.class, Where.user());
        sql.insert(list, 0);

    }
}
