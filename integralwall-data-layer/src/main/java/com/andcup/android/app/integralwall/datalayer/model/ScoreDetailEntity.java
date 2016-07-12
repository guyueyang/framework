package com.andcup.android.app.integralwall.datalayer.model;
import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/23.
 */
public class ScoreDetailEntity extends IntegralWallEntity implements Serializable {
    public static final int TYPE_IN  = 1;
    public static final int TYPE_OUT = 2;
    public static final int TYPE_RED_PACKETS = 3;

    @Column(name = DbColumn.USER_UID)
    String mUid;

    @Column(name = DbColumn.TYPE)
    int    mType;

    @Column(name = DbColumn.DATE)
    String mDate;

    @JsonProperty("name")
    @Column
    String mName;

    @JsonProperty("score")
    @Column
    String mScore;

    @JsonProperty("add_time")
    @Column
    String mAddTime;

    @JsonProperty("exchange_status")
    @Column
    String mExchangeStatus;

    @JsonProperty("image")
    @Column
    String mImage;

    public String getImage() {
        return mImage;
    }

    public void setUid(String mUid) {
        this.mUid = mUid;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getName() {
        return mName;
    }

    public String getScore() {
        return mScore;
    }

    public String getAddTime() {
        return mAddTime;
    }

    public String getExchangeStatus() {
        return mExchangeStatus;
    }

    public int getType() {
        return mType;
    }
}
