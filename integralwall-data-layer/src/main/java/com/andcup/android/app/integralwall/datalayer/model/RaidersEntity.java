package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.database.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/24.
 */
public class RaidersEntity extends IntegralWallEntity {

    public static final int NEW_USER = 0;   //新手篇
    public static final int SUPER_USER = 1; //高手篇

    @JsonProperty("id")
    @Column
    String mItemId;

    @JsonProperty("type")
    @Column
    int mType;//NEW_USER, SUPER_USER

    @JsonProperty("title")
    @Column
    String mTitle;

    @JsonProperty("url")
    @Column
    String mUrl;

    @JsonProperty("sort")
    int mSort;

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public boolean isSuperUser() {
        return mType == SUPER_USER;
    }
}
