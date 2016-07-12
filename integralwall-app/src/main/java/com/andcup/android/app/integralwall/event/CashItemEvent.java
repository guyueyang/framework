package com.andcup.android.app.integralwall.event;

import com.andcup.android.app.integralwall.datalayer.UserProvider;

import java.io.Serializable;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/22.
 */
public class CashItemEvent implements Serializable {

    String mItem;
    int    mScore;

    public CashItemEvent(String index, int score){
        mItem = index;
        mScore= score;
    }

    public String getItem() {
        return mItem;
    }

    public int getScore() {
        return mScore;
    }

    public boolean isEnoughScore(){
        try{
            return mScore <= Integer.parseInt(UserProvider.getInstance().getUserInfo().getScore());
        }catch (Exception e){

        }
        return false;
    }
}
