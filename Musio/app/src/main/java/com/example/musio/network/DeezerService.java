/*
package com.example.musio.network;

import android.content.Context;
import android.util.Log;

import com.example.musio.models.deezerData.DataSearchAlbum;
import com.example.musio.models.deezerData.DataSearchArtist;
import com.example.musio.models.deezerData.DataSearchTrack;


import java.net.URLEncoder;

public class DeezerService {
    private static final String TAG = "DeezerService";

    private DeezerService() {

    }

    public static void searchAuthor(Context context,
                             String artist,
                             Response.Listener<DataSearchArtist> response,
                             Response.ErrorListener errorListener) {
        Log.d(TAG, ">>searchAuthor artist="+artist);

        final String url = "https://api.deezer.com/search/artist?q=" + URLEncoder.encode(artist);
        Log.d(TAG, "searchAuthor " + url);

        RequestQueue queue = Volley.newRequestQueue(context);

        GsonRequest<DataSearchArtist> gsonRequest = new GsonRequest<>(url, DataSearchArtist.class, null,
                response, errorListener);

        queue.add(gsonRequest);
    }

    public static void searchAlbum(Context context,
                                   String album,
                                   Response.Listener<DataSearchAlbum> response,
                                   Response.ErrorListener errorListener) {
        Log.d(TAG, ">>searchAlbum album="+album);

        final String url = "https://api.deezer.com/search/album?q=" + URLEncoder.encode(album);
        Log.d(TAG, "searchAlbum " + url);

        RequestQueue queue = Volley.newRequestQueue(context);

        GsonRequest<DataSearchAlbum> gsonRequest = new GsonRequest(url, DataSearchAlbum.class,null, response,errorListener);

        queue.add(gsonRequest);
    }

    public static void searchAlbumTrack(Context context,
                                   int album,
                                   Response.Listener<DataSearchTrack> response,
                                   Response.ErrorListener errorListener) {
        Log.d(TAG, ">>searchAlbumTrack albumTrack="+album);

        final String url ="https://api.deezer.com/album/"+ album + "/tracks";
        Log.d(TAG, "searchAlbumTrack " + url);

        RequestQueue queue = Volley.newRequestQueue(context);

        GsonRequest<DataSearchTrack> gsonRequest = new GsonRequest(url, DataSearchTrack.class,null, response,errorListener);

        queue.add(gsonRequest);
    }

//    public static void searchTrack(Context context,
//                                   String track,
//                                   Response.Listener<DataSearchTrack> response,
//                                   Response.ErrorListener errorListener) {
//        Log.d(TAG, ">>searchTrack track="+track);
//
//        final String url ="https://api.deezer.com/search/track?q="+ URLEncoder.encode(track);
//        Log.d(TAG, "searchTrack " + url);
//
//        RequestQueue queue = Volley.newRequestQueue(context);
//
//        GsonRequest<DataSearchTrack> gsonRequest=new GsonRequest(url, DataSearchTrack.class,null,
//                response,errorListener);
//
//        queue.add(gsonRequest);
//    }
}
*/
