package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.database.activeandroid.annotation.Column;
import com.andcup.android.database.activeandroid.annotation.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Amos on 2015/9/4.
 */
@Table(name = "TaskFeatureEntity")
public class TaskFeatureEntity extends TaskEntity implements Serializable {

    @JsonProperty("type")
    @Column
    int    mFeatureType; // 1: normal, 2: quick

    public int getFeatureType() {
        return mFeatureType;
    }
}
