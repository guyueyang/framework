package com.andcup.android.app.integralwall.view.task.union;

/**
 * Created by Administrator on 2016/3/17.
 */
public class UnionTaskItem <T> {

    T mT;

    public UnionTaskItem(T t){
        mT= t;
    }

    public T getData() {
        return mT;
    }

}
