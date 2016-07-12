package com.andcup.android.app.integralwall.datalayer.model;


import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;

import java.io.Serializable;

/**
 * Created by Amos on 2015/9/14.
 */
public class SignedDateEntity extends IntegralWallEntity implements Serializable {

    @Column(name = DbColumn.USER_UID)
    String mUserId;

    @Column(name = DbColumn.YEAR_MONTH)
    String mYearMonth;

    @Column(name = DbColumn.YEAR_MONTH_DAY)
    String mDate;

    public void setYearMonth(String mYearMonth) {
        this.mYearMonth = mYearMonth;
    }

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getDate() {
        return mDate;
    }
}
