package com.example.musio.mediaplayer;

import android.media.MediaPlayer;

public enum MediaPlayerSingleton {
    INSTANCE;
    public MediaPlayer mp = new MediaPlayer();
}