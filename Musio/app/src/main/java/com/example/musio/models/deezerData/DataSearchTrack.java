package com.example.musio.models.deezerData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataSearchTrack {

    @SerializedName("data")
    @Expose
    private List<Track> data = null;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<Track> getData() {
        return data;
    }

    public void setData(List<Track> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}