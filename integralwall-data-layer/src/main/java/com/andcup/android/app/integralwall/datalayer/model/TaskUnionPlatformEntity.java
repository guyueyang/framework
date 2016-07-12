package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/16.
 */
public class TaskUnionPlatformEntity extends IntegralWallEntity implements Serializable {

    @Column(name = DbColumn.USER_UID)
    String mUid;
    @JsonProperty("id")
    @Column
    String mTaskId;
    @JsonProperty("name")
    @Column
    String mTaskName;
    @JsonProperty("icon")
    @Column
    String mIcon;
    @JsonProperty("task_number")
    @Column
    String mTaskNumber;
    @JsonProperty("total_score")
    @Column
    String mTotalScore;

    public String getmUid() {
        return mUid;
    }

    public String getmTaskId() {
        return mTaskId;
    }

    public String getmIcon() {
        return mIcon;
    }

    public String getmTaskNumber() {
        return mTaskNumber;
    }

    public String getmTotalScore() {
        return mTotalScore;
    }

    public void setUid(String mUid) {
        this.mUid = mUid;
    }

    public String getmTaskName() {
        return mTaskName;
    }
}
