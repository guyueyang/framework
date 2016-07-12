package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Amos on 2015/8/31.
 */
public class RankEntity extends IntegralWallEntity implements Serializable {

    public static final String WEEK  = "week";
    public static final String MONTH = "month";

    @JsonProperty("uid")
    @Column(name = DbColumn.USER_UID)
    String mUid;

    @JsonProperty("rank")
    @Column
    private int mRank;

    @JsonProperty("nickname")
    @Column
    private String mNickName;

    @JsonProperty("avatar")
    @Column
    private String mAvatar;

    @JsonProperty("score")
    @Column
    private String mScore;

    @Column(name = DbColumn.RANK_TYPE)
    private String mRankType;

    public String getAvatar() {
        return mAvatar;
    }

    public String getNickName() {
        return mNickName;
    }

    public int getRank() {
        return mRank;
    }

    public String getScore() {
        return mScore;
    }

    public void setRankType(String rankType) {
        this.mRankType = rankType;
    }

    public String getUid() {
        return mUid;
    }
}
