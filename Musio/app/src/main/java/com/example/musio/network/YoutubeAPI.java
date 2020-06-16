package com.example.musio.network;

import com.example.musio.models.ModelSongList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class YoutubeAPI {

    public static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    public static final String CHANNEL_ID = "UC6pPKYjldMfxcHASuAT-vLw";
    public static final String KEY = "key=AIzaSyAw7zNhlEoJYLbg4fbE7VPQ0y4V50GVsNY";
    public static final String sch = "search?";
    public static final String chid = "&channelId=" + CHANNEL_ID;
    public static final String mx = "&maxResults=10";
    public static final String ord = "&order=date";
    public static final String part = "&part=snippet";
    public static final String NPT = "&pageToken=";

    public static final String ply = "playlists?";
    public static final String part_ply = "&part=snippet,contentDetails";

    public static final String query = "&q=";
    public static final String type = "&type=video";

    public static final String CH = "channels?";
    public static final String IDC = "&id=" + CHANNEL_ID;
    public static final String CH_PART = "&part=snippet,statistics,brandingSettings";


    public interface Video {
        @GET
        Call<ModelSongList> getHomeVideo(@Url String url);

        /*@GET
        Call<ModelPlaylist> getPlaylist(@Url String url);

        @GET
        Call<ModelChannel> getChannel(@Url String url);*/
    }

    private static Video video = null;

    public static Video getVideo(){
        if (video == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            video = retrofit.create(Video.class);
        }
        return video;
    }

}
