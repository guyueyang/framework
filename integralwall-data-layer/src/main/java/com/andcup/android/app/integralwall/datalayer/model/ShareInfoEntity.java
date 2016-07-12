package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.Model;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/25.
 */
public class ShareInfoEntity extends IntegralWallEntity {

    @Column(name = DbColumn.USER_UID)
    String mUid;

    @JsonProperty("share_title")
    @Column
    String mTitle;

    @JsonProperty("share_content")
    @Column
    String mContent = "abc";

    @JsonProperty("share_url")
    @Column
    String mUrl = "http://www.baidu.com";

    @JsonProperty("share_image")
    @Column
    String mShareImage = "http://ico.ooopic.com/ajax/iconpng/?id=119463.png";

    public String getContent() {
        return mContent;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getShareImage() {
        return mShareImage;
    }

    public void setShareImage(String mShareImage) {
        this.mShareImage = mShareImage;
    }

    public void setUid(String mUid) {
        this.mUid = mUid;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }
}