package com.example.musio.utility;

import android.media.MediaPlayer;

public enum MediaPlayerSingleton {
    INSTANCE;
    public MediaPlayer mp = new MediaPlayer();
}
