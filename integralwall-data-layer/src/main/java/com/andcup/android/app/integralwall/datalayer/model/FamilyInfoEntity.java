package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos on 2015/9/7.
 */
public class FamilyInfoEntity extends IntegralWallEntity implements Serializable {

    public static final int LEVEL_ALL   = 0;
    public static final int LEVEL_FIRST = 1;
    public static final int LEVEL_SECOND = 2;
    public static final int LEVEL_THREE = 3;

    @Column(name = DbColumn.USER_UID)
    String mUid;

    @JsonProperty("total_member")
    @Column
    int mTotalMember;
    @JsonProperty("first_level_member")
    @Column
    int mFirstLevelTotal;
    @JsonProperty("first_level_member_score")
    @Column
    int mFirstLevelScore;

    @JsonProperty("second_level_member")
    @Column
    int mSecondLevelTotal;

    @JsonProperty("second_level_member_score")
    @Column
    int mSecondLevelScore;

    @JsonProperty("third_level_member")
    @Column
    int mThirdLevelTotal;

    @JsonProperty("total_task")
    @Column
    int mTotalTask;

    @JsonProperty("completed_task")
    @Column
    int mCompleteTask;

    @JsonProperty("uncompleted_task")
    @Column
    int mUnCompleteTask;

    @JsonProperty("total_score")
    @Column
    int mTotalCommission;
    @JsonProperty("my_score")
    @Column
    String mMyCommission;
    @JsonProperty("earned_score")
    @Column
    String mEarnedCommission;

    @JsonProperty("unavailable_score")
    @Column
    String mUnavailableCommission;

    @JsonProperty("family_member_feed")
    @Column(isJsonText = true, collection = ArrayList.class, element = MemberFeed.class)
    List<MemberFeed> mFamilyDynamic;

    public static class MemberFeed{
        @JsonProperty("nickname")
        String mNickName;
        @JsonProperty("dateline")
        String mDate;
        @JsonProperty("content")
        String mContent;

        public String getNickName() {
            return mNickName;
        }

        public String getDate() {
            return mDate;
        }

        public String getContent() {
            return mContent;
        }
    }

    public List<MemberFeed> getFamilyDynamic() {
        return mFamilyDynamic;
    }

    public int getFirstLevelScore() {
        return mFirstLevelScore;
    }

    public int getSecondLevelScore() {
        return mSecondLevelScore;
    }

    public void setUid(String uid) {
        this.mUid = uid;
    }

    public String getUid() {
        return mUid;
    }

    public int getTotalCommission() {
        return mTotalCommission;
    }

    public int getTotalMember() {
        return mTotalMember;
    }

    public int getFirstLevelTotal() {
        return mFirstLevelTotal;
    }

    public int getSecondLevelTotal() {
        return mSecondLevelTotal;
    }

    public int getThirdLevelTotal() {
        return mThirdLevelTotal;
    }

    public String getEarnedCommission() {
        return mEarnedCommission;
    }

    public String getMyCommission() {
        return mMyCommission;
    }

    public int getCompleteTask() {
        return mCompleteTask;
    }

    public int getUnCompleteTask() {
        return mUnCompleteTask;
    }

    public int getTotalTask() {
        return mTotalTask;
    }

    public String getUnavailableCommission() {
        return mUnavailableCommission;
    }
}
