package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.Model;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.io.Serializable;

/**
 * Created by Amos on 2015/9/29.
 */
public class ExperienceEntity extends Model implements Serializable {

    @Column(name = DbColumn.USER_UID)
    String mUid;

    @JsonProperty("get_time")
    @Column
    String mGetTime;

    @JsonProperty("source")
    @Column
    String mSource;

    @JsonProperty("experience")
    @Column
    String mExperience;

    public void setUid(String mUid) {
        this.mUid = mUid;
    }

    public String getGetTime() {
        return mGetTime;
    }

    public String getSource() {
        return mSource;
    }

    public String getExperience() {
        return mExperience;
    }

    public void setGetTime(String mGetTime) {
        this.mGetTime = mGetTime;
    }

    public void setSource(String mSource) {
        this.mSource = mSource;
    }

    public void setExperience(String mExperience) {
        this.mExperience = mExperience;
    }
}
