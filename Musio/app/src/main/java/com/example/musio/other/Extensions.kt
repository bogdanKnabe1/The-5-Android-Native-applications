package com.example.musio.other

import android.content.res.Resources
import android.support.v4.media.session.PlaybackStateCompat

fun getString(resourceId: Int) = Resources.getSystem().getString(resourceId)

// if we have one one this state's we are ready for playing
inline val PlaybackStateCompat.isPrepared
    get() = state == PlaybackStateCompat.STATE_BUFFERING ||
            state == PlaybackStateCompat.STATE_PLAYING ||
            state == PlaybackStateCompat.STATE_PAUSED

inline val PlaybackStateCompat.isPlaying
    get() = state == PlaybackStateCompat.STATE_BUFFERING ||
            state == PlaybackStateCompat.STATE_PLAYING

// if we in state pause to actually enable PLAY, otherwise we playing and we can only PAUSE.
inline val PlaybackStateCompat.isPlayEnabled
    get() = actions and PlaybackStateCompat.ACTION_PLAY != 0L ||
            (actions and PlaybackStateCompat.ACTION_PLAY_PAUSE != 0L &&
                state == PlaybackStateCompat.STATE_PAUSED)