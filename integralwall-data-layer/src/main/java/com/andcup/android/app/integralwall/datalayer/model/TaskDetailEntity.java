package com.andcup.android.app.integralwall.datalayer.model;


import android.text.TextUtils;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.Model;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.andcup.android.database.activeandroid.annotation.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos on 2015/9/11.
 */
@Table(name = "TaskDetailEntity")
public class TaskDetailEntity extends IntegralWallEntity implements Serializable {

    @Column(name = DbColumn.USER_UID)
    String mUserId;

    @JsonProperty("id")
    @Column(name = DbColumn.TASK_ID)
    String mTaskId;

    @JsonProperty("cid")
    @Column
    int   mTaskType;

    @JsonProperty("name")
    @Column
    String mTaskName;

    @JsonProperty("banner_image")
    @Column
    String mBannerImage;

    @JsonProperty("start_time")
    @Column
    String mStartTime;

    @JsonProperty("end_time")
    @Column
    String mEndTime;

    @JsonProperty("surplus_quota")
    @Column
    String mSurplusQuota;

    @JsonProperty("reward_intro")
    @Column
    String mRewardIntro;

    @JsonProperty("activity_intro")
    @Column
    String mActivityIntro;

    @JsonProperty("app_path")
    @Column
    String mAppPath;

    @JsonProperty("app_uri")
    @Column
    String mAppUri;

    @JsonProperty("my_task_option_id")
    @Column
    String mTaskOptionId;

    @JsonProperty("my_task_score")
    @Column
    String mTaskScore;

    @JsonProperty("app_type")
    @Column
    int   mAppType;

    @JsonProperty("app_size")
    @Column
    String mAppSize;

    @JsonProperty("task_total_score")
    @Column
    String mTaskTotalScore;

    @JsonProperty("task_option")
    @Column(isJsonText = true, collection = ArrayList.class, element = TaskDetailEntity.TaskOption.class)
    List<TaskDetailEntity.TaskOption> mTaskOptions;

    @JsonProperty("completed_info_fieldname")
    @Column(isJsonText = true, collection = ArrayList.class, element = String.class)
    List<String> mCompletedFields;

    @JsonProperty("image")
    @Column
    String mImageUrl;

    @JsonProperty("today_is_completed")
    @Column
    int mTodayIsCompleted; //

    @JsonProperty("app_screenshot")
    @Column(isJsonText = true, collection = ArrayList.class, element = String.class)
    List<String> mSnapshots;

    public List<String> getSnapshots() {
        return mSnapshots;
    }

    public boolean isTodayCompleted() {
        return mTodayIsCompleted == 1 || isOptionVarify();
    }

    public List<String> getCompletedFields() {
        return mCompletedFields;
    }

    public String getTaskName() {
        return mTaskName;
    }

    public String getAppPath() {
        return mAppPath;
    }

    public String getAppUri() {
        return mAppUri;
    }

    public String getTaskId() {
        return mTaskId;
    }

    public int getTaskType() {
        return mTaskType;
    }

    public List<TaskDetailEntity.TaskOption> getTaskOptions() {
        return mTaskOptions;
    }

    public static class TaskOption extends Model implements Serializable {
        /**未完成.*/
        public static final int STATUS_NOT_COMPLETE = 0;
        /**已完成.*/
        public static final int STATUS_COMPLETE = 1;
        /**待审核.*/
        public static final int STATUS_VERIFY = 2;
        /**审核失败.*/
        public static final int STATUS_VERIFY_FAILED = -1;

        @JsonProperty("option_id")
        String optionId;

        @JsonProperty("title")
        String title;

        @JsonProperty("days")
        String days;

        @JsonProperty("score")
        String score;

        @JsonProperty("residence_time")
        int    residenceTime;
        @JsonProperty("status")
        int    status;/** 0: 未完成. 1 已完成. 2. 待审核. -1 审核不通过.*/

        @JsonProperty("screenshot_request")
        String    screenShotRequest;

        public String getTitle() {
            return title;
        }

        public String getOptionId() {
            return optionId;
        }

        public String getDays() {
            return days;
        }

        public String getScore() {
            return score;
        }

        public int getResidenceTime() {
            return residenceTime;
        }

        public String getScreenShotRequest() {
            return screenShotRequest;
        }

        public boolean isRequestScreenShot(){
            try{
                return !TextUtils.isEmpty(screenShotRequest.trim());
            }catch (Exception e){

            }
            return false;
        }

        public int getStatus() {
            return status;
        }
    }

    public void setUserId(String userId) {
        this.mUserId = userId;
    }

    public String getActivityIntro() {
        return mActivityIntro;
    }

    public String getRewardIntro() {
        return mRewardIntro;
    }

    public String getTaskOptionId() {
        return mTaskOptionId;
    }


    public String getBannerImage() {
        return mBannerImage;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public String getSurplusQuota() {
        return mSurplusQuota;
    }

    public void setTaskOptionId(String taskOptionId) {
        this.mTaskOptionId = taskOptionId;
    }

    public String getTaskScore() {
        return mTaskScore;
    }

    public void setTaskScore(String taskScore) {
        this.mTaskScore = taskScore;
    }

    public String getAppSize() {
        return mAppSize;
    }

    public String getTaskTotalScore() {
        return mTaskTotalScore;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getOptionTitle(){
        for(int i = 0; null != mTaskOptions && i< mTaskOptions.size(); i++){
            if(mTaskOptions.get(i).getOptionId().equals(mTaskOptionId)){
                return mTaskOptions.get(i).title;
            }
        }
        return null;
    }

    /**是否在审核状态.*/
    public boolean isOptionVarify(){
        for(int i = 0; null != mTaskOptions && i< mTaskOptions.size(); i++){
            if(mTaskOptions.get(i).getOptionId().equals(mTaskOptionId)){
                return mTaskOptions.get(i).getStatus() == TaskOption.STATUS_VERIFY;
            }
        }
        return false;
    }

    public boolean isOptionSnapshot(){
        for(int i = 0; null != mTaskOptions && i< mTaskOptions.size(); i++){
            if(mTaskOptions.get(i).getOptionId().equals(mTaskOptionId)){
                 return !TextUtils.isEmpty(mTaskOptions.get(i).getScreenShotRequest());
            }
        }
        return false;
    }

    public String getOptionScore(){
        for(int i = 0; null != mTaskOptions && i< mTaskOptions.size(); i++){
            if(mTaskOptions.get(i).getOptionId().equals(mTaskOptionId)){
                return mTaskOptions.get(i).score;
            }
        }
        return "0";
    }

    public boolean isFirstDayTask(){
        if( null != mTaskOptions && mTaskOptions.get(0).getOptionId().equals(mTaskOptionId)){
            return true;
        }
        return false;
    }
}
