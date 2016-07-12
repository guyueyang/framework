package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/18.
 */
public class TaskBusinessEntity extends IntegralWallEntity{
    //task usier id
    @Column(name = DbColumn.USER_UID)
    String mUserId;

    @Column(name = DbColumn.TASK_ID)
    String mTaskId;

    @Column(name = DbColumn.OPTION_ID)
    String mTaskOptionId;

    @Column
    String mApkPackage;

    @Column
    long   mTimeStart = 0;

    @Column
    long   mTimeEnd = 0;

    @Column
    long  mLimitRunTime;

    @Column
    long  mScore;

    @Column(name = DbColumn.DATE)
    String mDate;

    @Column
    String mAppPath;

    @Column
    String mOptionInfo;

    @Column
    String mMark;

    @Column
    boolean mIsNeedSnapshot = false;

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public long getLimitRunTime() {
        return mLimitRunTime;
    }

    public long getRunTime() {
        return mTimeEnd - mTimeStart;
    }

    public boolean isFinish(){
        return getRunTime() - mLimitRunTime >= 0;
    }

    public String getTaskId() {
        return mTaskId;
    }

    public String getUserId() {
        return mUserId;
    }

    public String getTaskOptionId() {
        return mTaskOptionId;
    }

    public long getTimeStart() {
        return mTimeStart;
    }

    public long getTimeEnd() {
        return mTimeEnd;
    }

    public String getApkPackage() {
        return mApkPackage;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public void setTimeStart(long timeStart) {
        this.mTimeStart = timeStart;
        setTimeEnd(0);
    }

    public void setTimeEnd(long timeEnd) {
        this.mTimeEnd = timeEnd;
    }

    public void setOptionInfo(String mOptionInfo) {
        this.mOptionInfo = mOptionInfo;
    }

    public String getOptionInfo() {
        return mOptionInfo;
    }

    public String getAppPath() {
        return mAppPath;
    }

    public void setLimitRunTime(long mLimitRunTime) {
        this.mLimitRunTime = mLimitRunTime * 1000;
    }

    public void setScore(long mScore) {
        this.mScore = mScore;
    }

    public void setApkPackage(String mApkPackage) {
        this.mApkPackage = mApkPackage;
    }

    public void setTaskOptionId(String mTaskOptionId) {
        this.mTaskOptionId = mTaskOptionId;
    }

    public void setTaskId(String mTaskId) {
        this.mTaskId = mTaskId;
    }

    public void setAppPath(String mAppPath) {
        this.mAppPath = mAppPath;
    }

    public long getScore() {
        return mScore;
    }

    public void setIsNeedSnapshot(boolean isNeedSnapshot) {
        this.mIsNeedSnapshot = isNeedSnapshot;
    }

    public boolean isNeedSnapshot() {
        return mIsNeedSnapshot;
    }

    public String getMark() {
        return mMark;
    }

    public void setMark(String mMark) {
        this.mMark = mMark;
    }
}
