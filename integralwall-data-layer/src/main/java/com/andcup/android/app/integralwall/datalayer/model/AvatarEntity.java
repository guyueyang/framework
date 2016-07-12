package com.andcup.android.app.integralwall.datalayer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Amos on 2015/9/17.
 */
public class AvatarEntity implements Serializable {

    @JsonProperty("avatar")
    String mAvatar;


    public String getAvatar() {
        return mAvatar;
    }
}
