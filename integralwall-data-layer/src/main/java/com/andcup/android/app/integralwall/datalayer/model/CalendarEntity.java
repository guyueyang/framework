package com.andcup.android.app.integralwall.datalayer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Amos on 2015/9/22.
 */
public class CalendarEntity implements Serializable {

    @JsonProperty("share_content")
    String mContent = "abc";

    @JsonProperty("share_url")
    String mUrl = "http://www.baidu.com";

    @JsonProperty("share_image")
    String mShareImage = "http://ico.ooopic.com/ajax/iconpng/?id=119463.png";

    @JsonProperty("share_title")
    String mTitle;

    @JsonProperty("total_score")
    String mTotalScore;

    public String getContent() {
        return mContent;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getShareImage() {
        return mShareImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getTotalScore() {
        return mTotalScore;
    }
}
