
package com.example.musio.models.deezerData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class DataSearchArtist {

    @SerializedName("data")
    private List<Artist> mData;
    @SerializedName("total")
    private Long mTotal;

    public List<Artist> getData() {
        return mData;
    }

    public void setData(List<Artist> data) {
        mData = data;
    }

    public Long getTotal() {
        return mTotal;
    }

    public void setTotal(Long total) {
        mTotal = total;
    }

}
