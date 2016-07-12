package com.andcup.android.integralwall.commons.tools;


import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Amos on 2015/9/1.
 */
public class Phone {

    public static final String PHONE = "^((14[0-9])|(17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";

    public static final String EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    public static boolean isPhoneNumber(String phone){
        if(TextUtils.isEmpty(phone)){
            return false;
        }
        Pattern p = Pattern.compile(PHONE);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile(EMAIL);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
