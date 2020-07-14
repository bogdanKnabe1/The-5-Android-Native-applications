
package com.example.musio.models.deezerData;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Artist implements Parcelable {

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

    protected Artist(Parcel in) {
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readLong();
        }
        mLink = in.readString();
        mName = in.readString();
        if (in.readByte() == 0) {
            mNbAlbum = null;
        } else {
            mNbAlbum = in.readLong();
        }
        if (in.readByte() == 0) {
            mNbFan = null;
        } else {
            mNbFan = in.readLong();
        }
        mPicture = in.readString();
        mPictureBig = in.readString();
        mPictureMedium = in.readString();
        mPictureSmall = in.readString();
        mPictureXl = in.readString();
        byte tmpMRadio = in.readByte();
        mRadio = tmpMRadio == 0 ? null : tmpMRadio == 1;
        mTracklist = in.readString();
        mType = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mId);
        }
        dest.writeString(mLink);
        dest.writeString(mName);
        if (mNbAlbum == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mNbAlbum);
        }
        if (mNbFan == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mNbFan);
        }
        dest.writeString(mPicture);
        dest.writeString(mPictureBig);
        dest.writeString(mPictureMedium);
        dest.writeString(mPictureSmall);
        dest.writeString(mPictureXl);
        dest.writeByte((byte) (mRadio == null ? 0 : mRadio ? 1 : 2));
        dest.writeString(mTracklist);
        dest.writeString(mType);
    }
}
