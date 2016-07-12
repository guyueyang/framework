package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.andcup.android.database.activeandroid.annotation.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/14.
 */

@Table(name = "UserInfoEntity")
public class UserInfoEntity extends IntegralWallEntity implements Serializable {

    public static final int SEX_NONE    = 0;
    public static final int SEX_FEMALE  = 1;
    public static final int SEX_MALE    = 2;

    public static final int STEP_TASK_DONE    = 10;

    @JsonProperty("uid")
    @Column(name = DbColumn.USER_UID)
    String mUid;

    @JsonProperty("avatar")
    @Column(name = DbColumn.USER_AVATAR)
    String mAvatar;

    @JsonProperty("nickname")
    @Column(name = DbColumn.USER_NICK_NAME)
    String mNickName;

    @JsonProperty("sex")
    @Column(name = DbColumn.USER_SEX)
    int     mSex;

    @JsonProperty("birthday")
    @Column(name = DbColumn.USER_BIRTHDAY)
    String mBirthday;

    @JsonProperty("mobile")
    @Column
    String mMobile;

    @JsonProperty("score")
    @Column
    String mScore;

    @JsonProperty("unavailable_score")
    @Column
    String mUnavailableScore;

    @JsonProperty("invite_uid")
    @Column
    String mInviteUid;

    @JsonProperty("level")
    @Column
    String mLevel;

    @JsonProperty("experience")
    @Column
    String mExperience;

    @JsonProperty("upgrade_progress")
    @Column
    String mUpdateProgress;

    @JsonProperty("next_level_experience")
    @Column
    String mNextLevelExperience;

    @JsonProperty("next_level")
    @Column
    String mNextLevel;

    @JsonProperty("last_login_time")
    @Column
    String mLastLoginTime;

    @JsonProperty("register_time")
    @Column
    String mRegisterTime;

    @JsonProperty("money")
    @Column
    String mMoney;

    @JsonProperty("invite_url")
    @Column
    String mInviteUrl;

    @JsonProperty("newbie_guide")
    @Column(name = DbColumn.USER_NEW_BIE)
    int    mFirstLandingStep;

    @JsonProperty("newbie_guide_complete_time")
    @Column
    String mNewbieGuideTime;

    @JsonProperty("disable_exchange")
    @Column
    int mDisableExchange; ////是否禁止兑换 1是，0否

    @JsonProperty("is_get_newbie_lucky_money")
    @Column
    int mIsGetNewbieLuckyMoney;////是否获得新手红包 1是，0否

    @JsonProperty("today_get_score")
    @Column
    int mTodayInScore;

    @JsonProperty("today_invite_user_number")
    @Column
    int mTodayNewMember;

    @JsonProperty("not_completed_task_number")
    @Column
    int mNumberOfNotCompletedTask;

    @JsonProperty("bussinesscard")
    @Column
    String mBussinessCard;

    public boolean isDisableExchange() {
        return mDisableExchange == 1;
    }

    public String getBussinessCard() {
        return mBussinessCard;
    }

    public String getInviteUid() {
        return mInviteUid;
    }

    public int getTodayNewMember() {
        return mTodayNewMember;
    }

    public int getTodayInScore() {
        return mTodayInScore;
    }

    public String getScore() {
        return mScore;
    }

    public int getNumberOfNotCompletedTask() {
        return mNumberOfNotCompletedTask;
    }

    public void setNumberOfNotCompletedTask(int numberOfNotCompletedTask) {
        this.mNumberOfNotCompletedTask = numberOfNotCompletedTask;
    }

    public void setInviteUid(String inviteUid) {
        this.mInviteUid = inviteUid;
    }

    public int getFirstLandingStep() {
        return mFirstLandingStep;
    }
    public boolean isFirstLandingFinish(){
        return mFirstLandingStep >= STEP_TASK_DONE;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public int getmSex() {
        return mSex;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }

    public void setmNickName(String mNickName) {
        this.mNickName = mNickName;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public String getMobile() {
        return mMobile;
    }

    public String getRegisterTime() {
        return mRegisterTime;
    }

    public String getLevel() {
        return mLevel;
    }

    public String getNextLevel() {
        return mNextLevel;
    }

    public String getUpdateProgress() {
        return mUpdateProgress;
    }

    public void setMobile(String mMobile) {
        this.mMobile = mMobile;
    }

    public void setBirthday(String mBirthday) {
        this.mBirthday = mBirthday;
    }

    public void setSex(int mSex) {
        this.mSex = mSex;
    }

    public String getExperience() {
        return mExperience;
    }

    public String getNextLevelExperience() {
        return mNextLevelExperience;
    }
}
