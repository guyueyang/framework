package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.base.AbsAuthEntry;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Amos on 2015/9/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeiboEntry extends AbsAuthEntry implements Serializable {

    int    mType = AUTH_TYPE_WEIBO;   //1 weibo 2 weinxin 3 qq

    @JsonProperty("uid")
    String mUid;
    @JsonProperty("location")
    String mLocation;
    @JsonProperty("screen_name")
    String mNickName;
    @JsonProperty("gender")
    String mSex;
    @JsonProperty("profile_image_url")
    String mAvatarUrl;
    @JsonProperty("description")
    String mDescription;

    public String getUid() {
        return mUid;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getSex() {
        return mSex;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public void setUid(String mUid) {
        this.mUid = mUid;
    }

    public void setAvatarUrl(String mAvatarUrl) {
        this.mAvatarUrl = mAvatarUrl;
    }

    public void setNickName(String mNickName) {
        this.mNickName = mNickName;
    }

    public int getType() {
        return mType;
    }


    public String getNickName() {
        return mNickName;
    }

    @Override
    public String getAvatar() {
        return mAvatarUrl;
    }
}
