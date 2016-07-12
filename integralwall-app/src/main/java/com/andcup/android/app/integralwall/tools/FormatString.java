package com.andcup.android.app.integralwall.tools;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.yl.android.cpa.R;

import static com.andcup.android.app.integralwall.IntegralWallApplication.*;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/15.
 */
public class FormatString {

    public static SpannableString newScore(String format, String value){
        SpannableString spannableString = new SpannableString(format);
        //text size
        int scoreEnd = value.length();
        spannableString.setSpan(new AbsoluteSizeSpan(22, true), 0, scoreEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(13, true), scoreEnd, format.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString newScore(String score){
        String format = getInstance().getResources().getString(R.string.format_score, score);
        SpannableString spannableString = new SpannableString(format);
        //text size
        int scoreEnd = score.length();
        spannableString.setSpan(new AbsoluteSizeSpan(22, true), 0, scoreEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(13, true), scoreEnd, format.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString newInviteScore(String score){
        String format = getInstance().getResources().getString(R.string.format_score, score);
        SpannableString spannableString = new SpannableString(format);
        //text size
        int scoreEnd = score.length();
        spannableString.setSpan(new RelativeSizeSpan(0.5f), scoreEnd, format.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString newMemberLevel(String format){
        SpannableString spannableString = new SpannableString(format);
        //text size
        int scoreEnd = 4;
        spannableString.setSpan(new RelativeSizeSpan(0.8f), scoreEnd, format.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getInstance().getResources().getColor(R.color.black_b2b2b2)), scoreEnd, format.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString newCalendar(String score){
        String format = getInstance().getResources().getString(R.string.mine_score_calendar, score);
        SpannableString spannableString = new SpannableString(format);
        int start = format.indexOf(score);
        spannableString.setSpan(new ForegroundColorSpan(getInstance().getResources().getColor(R.color.yellow_ff6836)), start, format.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString newCashing(String score){
        String format = getInstance().getResources().getString(R.string.mine_score_cash, score);
        SpannableString spannableString = new SpannableString(format);
        return spannableString;
    }

    public static SpannableString newRank(String score){
        String format = getInstance().getResources().getString(R.string.rank_score, score);
        SpannableString spannableString = new SpannableString(format);
        int start = format.indexOf(score);
        spannableString.setSpan(new ForegroundColorSpan(getInstance().getResources().getColor(R.color.yellow_ff6836)), start, start + score.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString newRankMine(String score){
        String format = getInstance().getResources().getString(R.string.rank_mine, score);
        SpannableString spannableString = new SpannableString(format);
        int start = format.indexOf(score);
        spannableString.setSpan(new ForegroundColorSpan(getInstance().getResources().getColor(R.color.yellow_ff6836)), start, start + score.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString newJoinDays(String days){
        String format = getInstance().getResources().getString(R.string.format_join_days, days);
        SpannableString spannableString = new SpannableString(format);
        int start = format.indexOf(days);
        spannableString.setSpan(new ForegroundColorSpan(getInstance().getResources().getColor(R.color.yellow_ff6836)), start, start + days.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString newCompleteTasks(String tasks){
        String format = getInstance().getResources().getString(R.string.format_complete_tasks, tasks);
        SpannableString spannableString = new SpannableString(format);
        int start = format.indexOf(tasks);
        spannableString.setSpan(new ForegroundColorSpan(getInstance().getResources().getColor(R.color.yellow_ff6836)), start, start + tasks.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString newOfferScores(String scores){
        String format = getInstance().getResources().getString(R.string.format_offer_score, scores);
        SpannableString spannableString = new SpannableString(format);
        int start = format.indexOf(scores);
        spannableString.setSpan(new ForegroundColorSpan(getInstance().getResources().getColor(R.color.yellow_ff6836)), start, start + scores.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
