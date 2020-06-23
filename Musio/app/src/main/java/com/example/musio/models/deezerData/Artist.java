
package com.example.musio.models.deezerData;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Artist {

    @SerializedName("id")
    private Long mId;
    @SerializedName("link")
    private String mLink;
    @SerializedName("name")
    private String mName;
    @SerializedName("nb_album")
    private Long mNbAlbum;
    @SerializedName("nb_fan")
    private Long mNbFan;
    @SerializedName("picture")
    private String mPicture;
    @SerializedName("picture_big")
    private String mPictureBig;
    @SerializedName("picture_medium")
    private String mPictureMedium;
    @SerializedName("picture_small")
    private String mPictureSmall;
    @SerializedName("picture_xl")
    private String mPictureXl;
    @SerializedName("radio")
    private Boolean mRadio;
    @SerializedName("tracklist")
    private String mTracklist;
    @SerializedName("type")
    private String mType;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getNbAlbum() {
        return mNbAlbum;
    }

    public void setNbAlbum(Long nbAlbum) {
        mNbAlbum = nbAlbum;
    }

    public Long getNbFan() {
        return mNbFan;
    }

    public void setNbFan(Long nbFan) {
        mNbFan = nbFan;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public String getPictureBig() {
        return mPictureBig;
    }

    public void setPictureBig(String pictureBig) {
        mPictureBig = pictureBig;
    }

    public String getPictureMedium() {
        return mPictureMedium;
    }

    public void setPictureMedium(String pictureMedium) {
        mPictureMedium = pictureMedium;
    }

    public String getPictureSmall() {
        return mPictureSmall;
    }

    public void setPictureSmall(String pictureSmall) {
        mPictureSmall = pictureSmall;
    }

    public String getPictureXl() {
        return mPictureXl;
    }

    public void setPictureXl(String pictureXl) {
        mPictureXl = pictureXl;
    }

    public Boolean getRadio() {
        return mRadio;
    }

    public void setRadio(Boolean radio) {
        mRadio = radio;
    }

    public String getTracklist() {
        return mTracklist;
    }

    public void setTracklist(String tracklist) {
        mTracklist = tracklist;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
