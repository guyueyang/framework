package com.andcup.android.app.integralwall.datalayer.job;

import com.andcup.android.app.integralwall.datalayer.IntegralWallJob;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.ExperienceEntity;
import com.andcup.android.app.integralwall.datalayer.model.ScoreDetailEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.frame.datalayer.sql.SqlAction;
import com.andcup.android.frame.datalayer.sql.action.SqlInsert;
import com.andcup.android.frame.datalayer.exception.JobException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/3/26.
 */
public class JobExperience extends IntegralWallJob<BaseListEntry<ExperienceEntity>>{

    int mPageIndex;
    int mPageSize;

    public JobExperience(int pageIndex,int pageSize){
        mPageIndex=pageIndex;
        mPageSize=pageSize;
    }

    @Override
    public BaseListEntry<ExperienceEntity> start() throws IOException {
        String uid= UserProvider.getInstance().getUid();
        Long time=getTimestamp();
        String sign=md5(uid+time+getKey());
        return apis().getMyExperience(uid,mPageIndex+1,mPageSize,time,sign).execute().body();
    }

    @Override
    public void finish(BaseListEntry<ExperienceEntity> experienceEntityBaseListEntry) throws JobException{
        SqlInsert<ExperienceEntity> dataOperator = new SqlInsert<>(ExperienceEntity.class, Where.user());

        List<ExperienceEntity> dateList = experienceEntityBaseListEntry.getBaseListData().getList();
        for(int i = 0; null != dateList && i< dateList.size(); i++){
            dateList.get(i).setUid(UserProvider.getInstance().getUid());
        }
        dataOperator.insert(dateList, mPageIndex * mPageSize);
    }
}
