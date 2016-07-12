package com.andcup.android.app.integralwall.tools;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.andcup.android.app.integralwall.view.usercenter.invite.RewardRulesFragment;

/**
 * Created by Administrator on 2016/3/29.
 */
public class ShuoMClickableSpan extends ClickableSpan {

    String string;
    FragmentManager mFm;
    public ShuoMClickableSpan(String str,FragmentManager fm){
        super();
        this.string = str;
        this.mFm = fm;
    }


    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.BLUE);

    }


    @Override
    public void onClick(View widget) {
        Dialog.RULES.build(RewardRulesFragment.class,null).show(mFm);
    }

}
