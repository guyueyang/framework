package com.andcup.android.app.integralwall.datalayer.model;


import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/24.
 */
public class RewardEntity extends IntegralWallEntity {

    @Column(name = DbColumn.USER_UID)
    String mUserId;

    @JsonProperty("id")
    @Column
    int mItemId;

    @JsonProperty("title")
    @Column
    String mTitle;

    @JsonProperty("content")
    @Column
    String mContent;

    @JsonProperty("url")
    @Column
    String mUrl;

    @JsonProperty("image")
    @Column
    String mImageUrl;

    @Column
    String mRule;

    public void setUserId(String userId) {
        this.mUserId = userId;
    }

    public int getItemId() {
        return this.mItemId;
    }

    public String getTitle( ) {
        return this.mTitle;
    }

    public String getContent( ) {
        return this.mContent;
    }

    public String getUrl( ) {
        return this.mUrl;
    }

    public void setRule(String rule) {
        this.mRule = rule;
    }

    public String getRule() {
        return mRule;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
