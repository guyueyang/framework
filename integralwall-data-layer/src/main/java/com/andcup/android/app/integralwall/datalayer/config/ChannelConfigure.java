package com.andcup.android.app.integralwall.datalayer.config;

import android.content.Context;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/29.
 */
public class ChannelConfigure extends ConfigureProvider implements ConfigureProvider.Provider{

    protected ChannelConfigure() {
    }

    static ChannelConfigure PROVIDER;

    public static ChannelConfigure gen(){
        if(null == PROVIDER){
            PROVIDER = new ChannelConfigure();
        }
        return PROVIDER;
    }

    public static ChannelConfigure getInstance() {
        return PROVIDER;
    }

    public Channel mChannel;

    public String getChannel(){
        return mChannel.getChannel();
    }

    public String getInviteCode(){
        return mChannel.getInviteCode();
    }

    @Override
    public  void build(Context context) {
        mChannel = build(context, "channels.json", Channel.class);
    }
}
