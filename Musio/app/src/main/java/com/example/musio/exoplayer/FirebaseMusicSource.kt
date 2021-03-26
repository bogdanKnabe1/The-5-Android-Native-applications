package com.example.musio.exoplayer

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.MediaMetadataCompat.*
import androidx.core.net.toUri
import com.example.musio.data.remote.MusicDatabase
import com.example.musio.exoplayer.State.*
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Implemented mechanism to get data from firebase and check it's state //

/**
    The mechanism is giving the state of loading music,
    when the music has loaded, we pass the action (lambda) with the STATE_INITIALIZED state,
    which means the music is ready and we can use it in the Service.
    When music is being published or loaded, the states are appropriate (CREATED, INITIALIZING)
 */

class FirebaseMusicSource(private val musicDatabase: MusicDatabase) {

    var songs = emptyList<MediaMetadataCompat>()

    suspend fun fetchMediaData() = withContext(Dispatchers.IO) {
        state = STATE_INITIALIZING
        val allSongs = musicDatabase.getAllSongs()
        songs = allSongs.map { song ->
            MediaMetadataCompat.Builder()
                    .putString(METADATA_KEY_ARTIST, song.subTitle)
                    .putString(METADATA_KEY_MEDIA_ID, song.mediaId)
                    .putString(METADATA_KEY_TITLE, song.title)
                    .putString(METADATA_KEY_DISPLAY_TITLE, song.title)
                    .putString(METADATA_KEY_DISPLAY_ICON_URI, song.imageUrl)
                    .putString(METADATA_KEY_MEDIA_URI, song.songUrl)
                    .putString(METADATA_KEY_ALBUM_ART_URI, song.imageUrl)
                    .putString(METADATA_KEY_DISPLAY_SUBTITLE, song.subTitle)
                    .putString(METADATA_KEY_DISPLAY_DESCRIPTION, song.subTitle)
                    .build()
        }
        // trigger setter in 'state'
        state = STATE_INITIALIZED
    }

    // provide DataSource factory method
    // making list of several music sources to one "album" concatenating source of songs
    fun asMediaSource(dataSourceFactory: DefaultDataSourceFactory) : ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()
        songs.forEach { song ->
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(song.getString(METADATA_KEY_MEDIA_URI).toUri())
            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        return concatenatingMediaSource
    }

    // single item in our list ( song, album, etc )
    // browser behavior
    fun asMediaItems() = songs.map { song ->
        val descriptions = MediaDescriptionCompat.Builder()
                .setMediaUri(song.getString(METADATA_KEY_MEDIA_URI).toUri())
                .setTitle(song.description.title)
                .setSubtitle(song.description.subtitle)
                .setMediaId(song.description.mediaId)
                .setIconUri(song.description.iconUri)
                .build()
        MediaBrowserCompat.MediaItem(descriptions, FLAG_PLAYABLE)
    }.toMutableList()

    // listen in data source (music source) when music is finished downloading
    private val onReadyListeners = mutableListOf<(Boolean) -> Unit>()

    private var state: State = STATE_CREATED
        set(value) {
            if (value == STATE_INITIALIZED || value == STATE_ERROR) {
                synchronized(onReadyListeners) {
                    field = value
                    onReadyListeners.forEach { listener ->
                        listener(state == STATE_INITIALIZED)
                    }
                }
            } else {
                field = value
            }
        }

    // call function to get ready songs
    fun whenReady(action: (Boolean) -> Unit): Boolean {
        return if (state == STATE_CREATED || state == STATE_INITIALIZING) {
            onReadyListeners += action
            false
        } else {
            action(state == STATE_INITIALIZED)
            true
        }
    }

}

enum class State {
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR
}