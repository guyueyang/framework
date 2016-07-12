package com.andcup.android.app.integralwall.datalayer.model;

import android.text.TextUtils;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Amos on 2015/9/17.
 */
public class CashingItemEntity extends IntegralWallEntity implements Serializable {

    public static final int TYPE_POINTS  = 1;
    public static final int TYPE_FARE    = 2;
    public static final int TYPE_CASHING_ALIPAY = 3;
    public static final int TYPE_CASHING_BANK   = 5;

    @Column(name = DbColumn.TYPE)
    int     mType;

    @JsonProperty("id")
    @Column
    String mExchangeId;

    @JsonProperty("name")
    @Column
    String mName;

    @JsonProperty("score")
    @Column
    String mNeedScore;

    public void setType(int mType) {
        this.mType = mType;
    }

    public String getName() {
        return mName;
    }

    public String getExchangeId() {
        return mExchangeId;
    }

    public int getScore() {
        if(TextUtils.isEmpty(mNeedScore)){
            return 0;
        }
        return Integer.parseInt(mNeedScore);
    }
}
