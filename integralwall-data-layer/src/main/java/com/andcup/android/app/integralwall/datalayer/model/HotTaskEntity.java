package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Amos on 2015/8/31.
 */
public class HotTaskEntity extends IntegralWallEntity implements Serializable {

    public static final int HOT_NORMAL  = 1;
    public static final int HOT_QUICK   = 2;

    @JsonProperty("id")
    @Column(name = DbColumn.TASK_ID)
    private int mTaskId;

    @JsonProperty("name")
    @Column
    private String mName;

    @JsonProperty("image")
    @Column
    private String mImage;

    @JsonProperty("intro")
    @Column
    private String mIntro;

    @JsonProperty("app_size")
    @Column
    private String mAppSize;

    @JsonProperty("score")
    @Column
    private int mLowestScore;

    @JsonProperty("type")
    @Column
    private int mTaskType;

    public int getTaskId() {
        return mTaskId;
    }

    public String getAppSize() {
        return mAppSize;
    }

    public String getImage() {
        return mImage;
    }

    public String getIntro() {
        return mIntro;
    }

    public int getLowestScore() {
        return mLowestScore;
    }

    public String getName() {
        return mName;
    }

    public int getTaskType() {
        return mTaskType;
    }
}
