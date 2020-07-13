package com.example.musio.models.deezerData;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Album implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("cover_small")
    @Expose
    private String coverSmall;
    @SerializedName("cover_medium")
    @Expose
    private String coverMedium;
    @SerializedName("cover_big")
    @Expose
    private String coverBig;
    @SerializedName("cover_xl")
    @Expose
    private String coverXl;
    @SerializedName("genre_id")
    @Expose
    private Integer genreId;
    @SerializedName("nb_tracks")
    @Expose
    private Integer nbTracks;
    @SerializedName("record_type")
    @Expose
    private String recordType;
    @SerializedName("tracklist")
    @Expose
    private String tracklist;
    @SerializedName("explicit_lyrics")
    @Expose
    private Boolean explicitLyrics;
    @SerializedName("artist")
    @Expose
    private Artist artist;
    @SerializedName("type")
    @Expose
    private String type;

    protected Album(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        link = in.readString();
        cover = in.readString();
        coverSmall = in.readString();
        coverMedium = in.readString();
        coverBig = in.readString();
        coverXl = in.readString();
        if (in.readByte() == 0) {
            genreId = null;
        } else {
            genreId = in.readInt();
        }
        if (in.readByte() == 0) {
            nbTracks = null;
        } else {
            nbTracks = in.readInt();
        }
        recordType = in.readString();
        tracklist = in.readString();
        byte tmpExplicitLyrics = in.readByte();
        explicitLyrics = tmpExplicitLyrics == 0 ? null : tmpExplicitLyrics == 1;
        type = in.readString();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    public String getCoverMedium() {
        return coverMedium;
    }

    public void setCoverMedium(String coverMedium) {
        this.coverMedium = coverMedium;
    }

    public String getCoverBig() {
        return coverBig;
    }

    public void setCoverBig(String coverBig) {
        this.coverBig = coverBig;
    }

    public String getCoverXl() {
        return coverXl;
    }

    public void setCoverXl(String coverXl) {
        this.coverXl = coverXl;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public Integer getNbTracks() {
        return nbTracks;
    }

    public void setNbTracks(Integer nbTracks) {
        this.nbTracks = nbTracks;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    public Boolean getExplicitLyrics() {
        return explicitLyrics;
    }

    public void setExplicitLyrics(Boolean explicitLyrics) {
        this.explicitLyrics = explicitLyrics;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(cover);
        dest.writeString(coverSmall);
        dest.writeString(coverMedium);
        dest.writeString(coverBig);
        dest.writeString(coverXl);
        if (genreId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(genreId);
        }
        if (nbTracks == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(nbTracks);
        }
        dest.writeString(recordType);
        dest.writeString(tracklist);
        dest.writeByte((byte) (explicitLyrics == null ? 0 : explicitLyrics ? 1 : 2));
        dest.writeString(type);
    }
}
