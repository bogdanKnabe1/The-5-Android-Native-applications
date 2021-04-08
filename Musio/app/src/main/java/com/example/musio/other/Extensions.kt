package com.example.musio.other

import android.content.res.Resources
import android.os.SystemClock
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.media.session.PlaybackStateCompat.STATE_PLAYING

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

// get CURRENT playback position
inline val PlaybackStateCompat.currentPlaybackPosition: Long
    // getting specific position and calculate position
    get() = if (state == STATE_PLAYING) {
        // getting time diff real time and last update
        val timeData = SystemClock.elapsedRealtime() - lastPositionUpdateTime
        (position + (timeData * playbackSpeed)).toLong()
    } else position