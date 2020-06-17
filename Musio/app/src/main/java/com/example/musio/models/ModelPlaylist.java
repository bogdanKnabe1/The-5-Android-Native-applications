package com.example.musio.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelPlaylist {

    @SerializedName("nextPageToken")
    @Expose
    private String nextPageToken;

    @SerializedName("items")
    @Expose
    private List<PlaylistItems> items;

    public ModelPlaylist() {
    }

    public ModelPlaylist(String nextPageToken, List<PlaylistItems> items) {
        this.nextPageToken = nextPageToken;
        this.items = items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<PlaylistItems> getItems() {
        return items;
    }

    public void setItems(List<PlaylistItems> items) {
        this.items = items;
    }
}
