package com.andcup.android.app.integralwall.datalayer.model.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Amos on 2015/9/3.
 */
public class BaseListData<T extends Serializable> implements Serializable {
    @JsonProperty("list")
    List<T> mList;
    @JsonProperty("total")
    int mTotalCount;
    @JsonProperty("content")
    String mContent;

    public List<T> getList() {
        return mList;
    }

    public int getTotalCount() {
        return mTotalCount;
    }

    public String getContent() {
        return mContent;
    }
}
