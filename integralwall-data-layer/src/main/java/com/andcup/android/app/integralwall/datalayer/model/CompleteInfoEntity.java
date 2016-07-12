package com.andcup.android.app.integralwall.datalayer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

/**
 * Created by Amos on 2015/9/25.
 */
public class CompleteInfoEntity implements Serializable {

    @JsonProperty("task_remaining_days")
    int mTaskRemainingDays;
    @JsonProperty("is_can_get_lucky_money")
    int mIsCanGetLuckyMoney;
    @JsonProperty("next_task_option_id")
    int mNextTaskOptionId;

    public int getTaskRemainingDays() {
        return mTaskRemainingDays;
    }

    public int getIsCanGetLuckyMoney(){
        return  mIsCanGetLuckyMoney;
    }
}
