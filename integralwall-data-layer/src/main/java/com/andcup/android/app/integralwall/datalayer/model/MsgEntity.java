package com.andcup.android.app.integralwall.datalayer.model;


import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/25.
 */
public class MsgEntity extends IntegralWallEntity{

    // system msg.
    public static final int TYPE_MSG_SYSTEM = 0;
    // user msg.
    public static final int TYPE_MSG_USER = 1;

    public static final int STATE_READ_NO = 0;
    public static final int STATE_READ_YES = 1;

    @Column(name = DbColumn.USER_UID)
    String mUid;

    @JsonProperty("noteid")
    @Column(name = DbColumn.MSG_ID)
    String mMsgId;

    @JsonProperty("title")
    @Column
    String mTitle;

    @JsonProperty("content")
    @Column
    String mContent;

    @JsonProperty("readyn")
    @Column(name = DbColumn.READED)
    int mStatus;

    @JsonProperty("time")
    @Column
    String mTime;

    @JsonProperty("type")
    @Column(name = DbColumn.TYPE)
    int mType;

    public String getTime() {
        return mTime;
    }

    public boolean isSystemMsg(){
        return mType == TYPE_MSG_SYSTEM;
    }

    public void setUid(String mUid) {
        this.mUid = mUid;
    }

    public String getContent() {
        return mContent;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getMsgId() {
        return mMsgId;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setReaded() {
        this.mStatus = STATE_READ_YES;
    }
}
