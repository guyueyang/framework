package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 用户信息
 * <p/>
 * Created by Amos on 15-4-25.
 */
public class MemberEntity extends IntegralWallEntity implements Serializable {

    @JsonProperty("uid")
    @Column(name = DbColumn.USER_UID)
    String mUid;

    @JsonProperty("avatar")
    @Column
    String mAvatar;

    @JsonProperty("nickname")
    @Column
    String mNickName;

    @JsonProperty("join_days")
    @Column
    String mJoinDays;

    @JsonProperty("complete_task_number")
    @Column
    String mCompleteTaskNumber;

    @JsonProperty("score")
    @Column
    String mOfferScore;

    @JsonProperty("level")
    @Column
    String mLevel;

    @JsonProperty("register_time")
    @Column
    String mRegisterTime;

    @Column(name = DbColumn.FAMILY_ID)
    String mFamilyId;

    @Column(name = DbColumn.FAMILY_LEVEL)
    int     mFamilyLevel;

    @Column(name = DbColumn.FIND_KEYWORD)
    String mKeyword;

    public String getOfferScore() {
        return mOfferScore;
    }

    public String getJoinDays() {
        return mJoinDays;
    }

    public String getCompleteTaskNumber() {
        return mCompleteTaskNumber;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public String getLevel() {
        return mLevel;
    }

    public String getNickName() {
        return mNickName;
    }

    public String getRegisterTime() {
        return mRegisterTime;
    }

    public String getUid() {
        return mUid;
    }

    public void   setFamilyId(String familyId) {
        this.mFamilyId = familyId;
    }

    public void setFamilyLevel(int familyLevel) {
        this.mFamilyLevel = familyLevel;
    }

    public void setKeyword(String keyword) {
        this.mKeyword = keyword;
    }
}