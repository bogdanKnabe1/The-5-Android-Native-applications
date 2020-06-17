package com.example.musio.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaylistDetail {

    @SerializedName("itemCount")
    @Expose
    private int itemCount;

    public PlaylistDetail() {
    }

    public PlaylistDetail(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}