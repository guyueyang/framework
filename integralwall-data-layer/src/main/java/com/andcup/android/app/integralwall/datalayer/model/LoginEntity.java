package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.Model;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.andcup.android.database.activeandroid.annotation.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/9.
 */

@Table(name = "LoginEntity")
public class LoginEntity extends IntegralWallEntity implements Serializable{

    public static final int USER_TYPE_VISITOR  = 0;
    public static final int USER_TYPE_AUTHOR   = 1;
    public static final int USER_TYPE_REGISTER = 2;

    @JsonProperty("uid")
    @Column(name = DbColumn.USER_UID)
    String mUid;

    @JsonProperty("uuid")
    @Column
    String mUuid;

    @JsonProperty("mobile")
    @Column
    String mMobile;

    @JsonProperty("nickname")
    @Column
    String mNickname;

    @JsonProperty("last_login_time")
    @Column
    String mLastLoginTime;

    @JsonProperty("reg_time")
    @Column
    String mRegTime;

    @JsonProperty("access_token")
    @Column
    String mAccessToken;

    @JsonProperty("register_time")
    @Column
    String mRegisterTime;

    @JsonProperty("disable_exchange")
    @Column
    String mDisableExchange;

    @JsonProperty("newbie_guide")
    @Column
    String mNewbieGuide;
    @JsonProperty("newbie_guide_complete_time")
    @Column
    String mNewBieGuideCompleteTime;

    @JsonProperty("is_get_newbie_lucky_money")
    @Column
    String mIsGetNewbieLuckyMoney;
    @JsonProperty("level")
    @Column
    String mLevel;

    @JsonProperty("register_imei")
    @Column
    String mRegisterImei;

    @Column(name = DbColumn.TYPE)
    int    mType = USER_TYPE_REGISTER;

    public String getUid() {
        return mUid;
    }

    public String getPhone() {
        return mMobile;
    }

    public String getNickName() {
        return mNickname;
    }

    public String getLastLoginTime(){
        return mLastLoginTime;
    }

    public String getRegTime() {
        return mRegTime;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public boolean isVisitor(){
        return mType == USER_TYPE_VISITOR;
    }

    public boolean isRegister(){
        return mType == USER_TYPE_REGISTER;
    }

    public void setNickname(String mNickname) {
        this.mNickname = mNickname;
    }
}
