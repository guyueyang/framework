package com.andcup.android.app.integralwall.view.share;

import java.io.Serializable;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/24.
 */
public class ShareContent implements Serializable {

    String mShareUrl;

    String mShareContent;

    String mShareAvatar;

    String mNewbieGuide;

    public ShareContent(String content, String url, String avatar, String newbieGuide){
        mShareUrl = url;
        mShareContent = content;
        mShareAvatar = avatar;
        mNewbieGuide=newbieGuide;
    }
}
