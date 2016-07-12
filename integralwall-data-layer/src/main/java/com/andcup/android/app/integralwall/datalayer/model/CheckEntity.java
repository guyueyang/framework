package com.andcup.android.app.integralwall.datalayer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Amos on 2015/11/27.
 */
public class CheckEntity implements Serializable {

    @JsonProperty("updateTime")
    long update;

    @JsonProperty("model")
    List<String> models;

    @JsonProperty("black")
    List<String> blacks;

    public List<String> getModels() {
        return models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }

    public void setBlacks(List<String> blacks) {
        this.blacks = blacks;
    }

    public List<String> getBlacks() {
        return blacks;
    }

    public void setUpdate(long update) {
        this.update = update;
    }

    public long getUpdate() {
        return update;
    }
}
