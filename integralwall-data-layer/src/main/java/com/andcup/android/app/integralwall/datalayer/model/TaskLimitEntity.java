package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.andcup.android.database.activeandroid.annotation.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/19.
 */
@Table(name = "TaskLimitEntity")
public class TaskLimitEntity extends TaskFeatureEntity{

    @Column(name = DbColumn.TASK_LIMIT)
    int mLimitTask;

    @JsonProperty("distance_start_time")
    @Column
    int mLimitTime;

    @JsonProperty("is_start")
    @Column
    int mIsStart; /**1:已经开始 0:未开始。*/

    @JsonProperty("total_quota")
    @Column
    int mTotalQuota;

    public int getTotalQuota() {
        return mTotalQuota;
    }

    public boolean isStart() {
        return mIsStart == 1;
    }
}
