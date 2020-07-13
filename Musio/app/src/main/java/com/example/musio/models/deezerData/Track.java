package com.example.musio.models.deezerData;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Track implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("readable")
    @Expose
    private Boolean readable;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("title_short")
    @Expose
    private String titleShort;
    @SerializedName("title_version")
    @Expose
    private String titleVersion;
    @SerializedName("isrc")
    @Expose
    private String isrc;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("track_position")
    @Expose
    private Integer trackPosition;
    @SerializedName("disk_number")
    @Expose
    private Integer diskNumber;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("explicit_lyrics")
    @Expose
    private Boolean explicitLyrics;
    @SerializedName("explicit_content_lyrics")
    @Expose
    private Integer explicitContentLyrics;
    @SerializedName("explicit_content_cover")
    @Expose
    private Integer explicitContentCover;
    @SerializedName("preview")
    @Expose
    private String preview;
    @SerializedName("artist")
    @Expose
    private Artist artist;
    @SerializedName("type")
    @Expose
    private String type;

    protected Track(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        byte tmpReadable = in.readByte();
        readable = tmpReadable == 0 ? null : tmpReadable == 1;
        title = in.readString();
        titleShort = in.readString();
        titleVersion = in.readString();
        isrc = in.readString();
        link = in.readString();
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        if (in.readByte() == 0) {
            trackPosition = null;
        } else {
            trackPosition = in.readInt();
        }
        if (in.readByte() == 0) {
            diskNumber = null;
        } else {
            diskNumber = in.readInt();
        }
        if (in.readByte() == 0) {
            rank = null;
        } else {
            rank = in.readInt();
        }
        byte tmpExplicitLyrics = in.readByte();
        explicitLyrics = tmpExplicitLyrics == 0 ? null : tmpExplicitLyrics == 1;
        if (in.readByte() == 0) {
            explicitContentLyrics = null;
        } else {
            explicitContentLyrics = in.readInt();
        }
        if (in.readByte() == 0) {
            explicitContentCover = null;
        } else {
            explicitContentCover = in.readInt();
        }
        preview = in.readString();
        type = in.readString();
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getReadable() {
        return readable;
    }

    public void setReadable(Boolean readable) {
        this.readable = readable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleShort() {
        return titleShort;
    }

    public void setTitleShort(String titleShort) {
        this.titleShort = titleShort;
    }

    public String getTitleVersion() {
        return titleVersion;
    }

    public void setTitleVersion(String titleVersion) {
        this.titleVersion = titleVersion;
    }

    public String getIsrc() {
        return isrc;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getTrackPosition() {
        return trackPosition;
    }

    public void setTrackPosition(Integer trackPosition) {
        this.trackPosition = trackPosition;
    }

    public Integer getDiskNumber() {
        return diskNumber;
    }

    public void setDiskNumber(Integer diskNumber) {
        this.diskNumber = diskNumber;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Boolean getExplicitLyrics() {
        return explicitLyrics;
    }

    public void setExplicitLyrics(Boolean explicitLyrics) {
        this.explicitLyrics = explicitLyrics;
    }

    public Integer getExplicitContentLyrics() {
        return explicitContentLyrics;
    }

    public void setExplicitContentLyrics(Integer explicitContentLyrics) {
        this.explicitContentLyrics = explicitContentLyrics;
    }

    public Integer getExplicitContentCover() {
        return explicitContentCover;
    }

    public void setExplicitContentCover(Integer explicitContentCover) {
        this.explicitContentCover = explicitContentCover;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
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
        //nothing special to here
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
        dest.writeByte((byte) (readable == null ? 0 : readable ? 1 : 2));
        dest.writeString(title);
        dest.writeString(titleShort);
        dest.writeString(titleVersion);
        dest.writeString(isrc);
        dest.writeString(link);
        if (duration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(duration);
        }
        if (trackPosition == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(trackPosition);
        }
        if (diskNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(diskNumber);
        }
        if (rank == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rank);
        }
        dest.writeByte((byte) (explicitLyrics == null ? 0 : explicitLyrics ? 1 : 2));
        if (explicitContentLyrics == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(explicitContentLyrics);
        }
        if (explicitContentCover == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(explicitContentCover);
        }
        dest.writeString(preview);
        dest.writeString(type);
    }
}