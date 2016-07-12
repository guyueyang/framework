package com.andcup.android.app.integralwall.view.usercenter.score;

/**
 * Created by Administrator on 2016/3/23.
 */
public class ScoreItem  <T> {

    T mT;

    public ScoreItem(T t){
        mT= t;
    }

    public T getData() {
        return mT;
    }

}
