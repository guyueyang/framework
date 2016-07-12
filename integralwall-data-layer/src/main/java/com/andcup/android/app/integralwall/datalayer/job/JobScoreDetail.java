package com.andcup.android.app.integralwall.datalayer.job;
import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.ScoreDetailEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.frame.datalayer.sql.SqlAction;
import com.andcup.android.frame.datalayer.sql.action.SqlInsert;
import com.andcup.android.frame.datalayer.exception.JobException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public class JobScoreDetail extends IntegralWallJob<BaseListEntry<ScoreDetailEntity>> {

    int mPageIndex;
    int mPageSize;
    int mType;
    String mDate;

    public JobScoreDetail(int pageIndex, int pageSize,int type,String date){
        mPageIndex=pageIndex;
        mPageSize=pageSize;
        mType=type;
        mDate=date;
    }
    @Override
    public BaseListEntry<ScoreDetailEntity> start() throws IOException {
        String uid = UserProvider.getInstance().getUid();
        long time=getTimestamp();
        String sign=md5(uid +mType+mDate+ time + getKey());
        return apis().getScoreDetail(uid,mType,mDate,mPageIndex+1,mPageSize,time,sign).execute().body();
    }

    @Override
    public void finish(BaseListEntry<ScoreDetailEntity> tasks) throws JobException {
        SqlInsert<ScoreDetailEntity> dataOperator = new SqlInsert<>(ScoreDetailEntity.class,Where.ScoreDetail(mType));

        List<ScoreDetailEntity> dateList = tasks.getBaseListData().getList();
        for(int i = 0; null != dateList && i< dateList.size(); i++){
            dateList.get(i).setUid(UserProvider.getInstance().getUid());
            dateList.get(i).setType(mType);
            dateList.get(i).setDate(mDate);
        }
        dataOperator.insert(dateList, mPageIndex * mPageSize);
    }
}
