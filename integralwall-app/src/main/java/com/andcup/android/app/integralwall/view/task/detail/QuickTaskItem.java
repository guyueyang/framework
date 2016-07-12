package com.andcup.android.app.integralwall.view.task.detail;


import com.andcup.android.app.integralwall.datalayer.model.TaskDetailEntity;

/**
 * Created by Amos on 2015/12/7.
 */
public class QuickTaskItem {

    public static final int TYPE_TITLE  = 0;
    public static final int TYPE_OPTION = 1;
    public static final int TYPE_SUMMARY   = 2;

    public enum  State {
        Active,
        Wait,
        Verify,
        Disable
    }

    int    mOptionId;
    String mTitle;
    String mScore;
    State  mState = State.Disable;

    TaskDetailEntity.TaskOption mTaskOption;

    int    mType = TYPE_OPTION;

    public static QuickTaskItem genOption(TaskDetailEntity.TaskOption option){
        QuickTaskItem item = new QuickTaskItem();
        item.mOptionId = Integer.parseInt(option.getOptionId());
        item.mScore    = option.getScore();
        item.mTitle    = option.getTitle();
        item.mType    = TYPE_OPTION;
        item.mTaskOption = option;
        return item;
    }

    public static QuickTaskItem genSummary(String summary){
        QuickTaskItem item = new QuickTaskItem();
        item.mTitle    = summary;
        item.mType    = TYPE_SUMMARY;
        return item;
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        this.mState = state;
        if(state == QuickTaskItem.State.Wait && mTaskOption.getStatus() == TaskDetailEntity.TaskOption.STATUS_VERIFY){
            mState = State.Verify;
        }
    }

    public String getScore() {
        return mScore;
    }

    public int getType() {
        return mType;
    }

    public String getTitle() {
        return mTitle;
    }

    public TaskDetailEntity.TaskOption getTaskOption() {
        return mTaskOption;
    }
}
