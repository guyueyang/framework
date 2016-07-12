package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Amos on 2015/8/31.
 */
public class TaskEntity extends IntegralWallEntity implements Serializable {

    public static final int TASK_TYPE_DEFAULT = 0;
    public static final int TASK_TYPE_SIGN_IN = 1;
    public static final int TASK_TYPE_DEEP    = 2;
    public static final int TASK_TYPE_QUICK   = 3;

    public static final int TASK_STATE_WAIT   = 0;
    public static final int TASK_STATE_VERIFY = 1;
    public static final int TASK_STATE_FAILED = 2;
    public static final int TASK_STATE_FINISH = 3;

    public static final int APP_TYPE_ANDROID = 1;
    public static final int APP_TYPE_IOS     = 2;
    public static final int APP_TYPE_WEB     = 3;

    public static final int TASK_NORMAL  = 1;
    public static final int TASK_QUICK   = 2;

    @Column(name = DbColumn.USER_UID)
    String mUid;

    @JsonProperty("id")
    @Column(name = DbColumn.TASK_ID)
    String mTaskId;
    @JsonProperty("name")
    @Column
    String mName;
    @JsonProperty("image")
    @Column
    String mImageUrl;
    @JsonProperty("intro")
    @Column
    String mIntro;
    @JsonProperty("app_size")
    @Column
    String mAppSize;
    @JsonProperty("lowest_score")
    @Column
    String mLowestScore;
    @JsonProperty("app_path")
    @Column
    String mAppPath;
    @JsonProperty("app_uri")
    @Column
    String mAppPackage;
    @JsonProperty("task_option_id")
    @Column
    String mOptionId;
    @Column(name = DbColumn.TASK_STATE)
    int  mTaskState;
    @JsonProperty("start_time")
    @Column
    String mStartTime;
    @JsonProperty("end_time")
    @Column
    String mEndTime;
    @JsonProperty("score")
    @Column
    String mScore;
    @JsonProperty("app_type")
    @Column
    int   mAppType;
    @JsonProperty("check_not_pass_reason")
    @Column
    String mVerifyNotPassed;
    @JsonProperty("total_score")
    @Column
    String mTotalScore;
    @JsonProperty("execution_days")
    @Column
    String mExecutionDays;
    @JsonProperty("total_days")
    @Column
    String mTotalDays;
    @JsonProperty("today_is_completed")
    @Column(name = DbColumn.TODAY_IS_COMPLETED)
    int    mTodayIsCompleted;

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getAppSize() {
        return mAppSize;
    }

    public String getIntro() {
        return mIntro;
    }

    public String getLowestScore() {
        return mLowestScore;
    }

    public String getName() {
        return mName;
    }

    public String getTaskId() {
        return mTaskId;
    }

    public String getPath(){
        return mAppPath;
    }

    public String getPackage(){
        return mAppPackage;
    }

    public String getAppPath( ) {
        return this.mAppPath;
    }

    public void setAppPackage(String appPackage) {
        this.mAppPackage = appPackage;
    }

    public String getOptionId() {
        return mOptionId;
    }

    public int getTaskState() {
        return mTaskState;
    }

    public void setTaskState(int taskState) {
        this.mTaskState = taskState;
    }

    public void setUid(String mUid) {
        this.mUid = mUid;
    }

    public void setTaskId(String taskId) {
        this.mTaskId = taskId;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public String getScore() {
        return mScore;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public void setEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    public boolean isWebApp(){
        return mAppType == APP_TYPE_WEB;
    }

    public String getVerifyNotPassed() {
        return mVerifyNotPassed;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setScore(String mScore) {
        this.mScore = mScore;
    }

    public boolean isComplete(){
        return mTodayIsCompleted == 1;
    }

    public String getTotalScore() {
        return mTotalScore;
    }

    public String getExecutionDays() {
        return mExecutionDays;
    }

    public String getTotalDays() {
        return mTotalDays;
    }
}
