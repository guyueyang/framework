package com.andcup.android.app.integralwall.datalayer.tools;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Amos on 2015/7/9.
 */
public class TimeUtils {

    static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String millisToDate(long millis){
        Date date = new Date(millis);
        return sDateFormat.format(date);
    }

    public static String millisToString(long millis, boolean text) {
        boolean negative = millis < 0;
        millis = Math.abs(millis);

        millis /= 1000;
        int sec = (int) (millis % 60);
        millis /= 60;
        int min = (int) (millis % 60);
        millis /= 60;
        int hours = (int) millis;

        String time;
        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        format.applyPattern("00");
        if (text) {
            if (millis > 0)
                time = (negative ? "0" : "") + hours + "小时" + format.format(min) + "分钟";
            else if (min > 0)
                time = (negative ? "0" : "") + min + "分钟" + format.format(sec) + "秒";
            else
                time = (negative ? "0" : "") + sec + "秒";
        }
        else {
            if (millis > 0)
                time = (negative ? "0" : "") + hours + ":" + format.format(min) + ":" + format.format(sec);
            else
                time = (negative ? "0" : "") + min + ":" + format.format(sec);
        }
        return time;
    }

    public static String getCurrentDay(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        return date;
    }

    public static String getDate(long mill){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        Date date = new Date(mill*1000);
        return sdf.format(date);
    }
}