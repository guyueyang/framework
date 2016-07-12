package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/22.
 */
public class FirstLandingTaskEntity extends IntegralWallEntity implements Serializable {
    public static final int FIRST_RECEIVED_SCORE=1;
    public static final int FIRST_PERSONEL_DATA=2;
    public static final int FIRST_QUICK_TASK=3;
    public static final int FIRST_UNION_TASK=4;
    public static final int FIRST_SHARE=5;
    public static final int FIRST_QB_EXCHANGE=6;

    @Column(name = DbColumn.USER_UID)
    String mUid;
    @JsonProperty("id")
    @Column
    String mId;
    @JsonProperty("task_id")
    @Column
    String mTaskId;
    @JsonProperty("title")
    @Column
    String mTitle;
    @JsonProperty("score")
    @Column
    String mScore;
    @JsonProperty("is_complete")
    @Column
    String mIsComplete;

    public String getTaskId() {
        return mTaskId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getScore() {
        return mScore;
    }

    public String getIsComplete() {
        return mIsComplete;
    }

    public String getmId() {
        return mId;
    }
}
