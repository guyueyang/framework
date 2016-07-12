package com.andcup.android.app.integralwall.datalayer.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/1/6.
 */
public class Channel {
    @JsonProperty("channel")
    String mChannel;
    @JsonProperty("inviteCode")
    String mInviteCode;


    public String getChannel() {
        return mChannel;
    }

    public String getInviteCode() {
        return mInviteCode;
    }
}
