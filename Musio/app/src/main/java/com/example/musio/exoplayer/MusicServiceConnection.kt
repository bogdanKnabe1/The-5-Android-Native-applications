package com.example.musio.exoplayer

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musio.R
import com.example.musio.other.Constants.NETWORK_ERROR
import com.example.musio.other.Event
import com.example.musio.other.Resource
import com.example.musio.other.getString

// communicator between Activity(fragment's inside) and MusicService
// tool to communicate
class MusicServiceConnection(
        context: Context
) {
    private val _isConnected = MutableLiveData<Event<Resource<Boolean>>>()
    val isConnected: LiveData<Event<Resource<Boolean>>> = _isConnected

    private val _networkError = MutableLiveData<Event<Resource<Boolean>>>()
    val networkError: LiveData<Event<Resource<Boolean>>> = _networkError

    private val _playBackState = MutableLiveData<PlaybackStateCompat?>()
    val playBackState: LiveData<PlaybackStateCompat?> = _playBackState

    private val _currentlyPlayingSong = MutableLiveData<MediaMetadataCompat?>()
    val currentlyPlayingSong: LiveData<MediaMetadataCompat?> = _currentlyPlayingSong

    lateinit var mediaController: MediaControllerCompat

    private val mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(context)

    private val mediaBrowser = MediaBrowserCompat(
            context,
            ComponentName(context, MusicService::class.java),
            mediaBrowserConnectionCallback,
            null
    ).apply { connect() }

    // only return when we access to that property
    // skips songs, pause, resume
    val transportControls: MediaControllerCompat.TransportControls
        get() = mediaController.transportControls

    // need to be called from viewModel
    fun subscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.subscribe(parentId, callback)
    }
    // same as subscribe
    fun unSubscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.unsubscribe(parentId, callback)
    }

    private inner class MediaBrowserConnectionCallback(
            private val context: Context
    ) : MediaBrowserCompat.ConnectionCallback() {
        // LISTEN FOR EVENTS ( connected, failed to connect or suspended )
        // once is MusicConnection is active it will be called
        override fun onConnected() {
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(MediaControllerCallback())
            }
            _isConnected.postValue(Event(Resource.success(true)))
        }

        override fun onConnectionSuspended() {
            _isConnected.postValue(Event(Resource.error(
                    getString(R.string.connection_suspended),
                    false
            )))
        }

        override fun onConnectionFailed() {
            _isConnected.postValue(Event(Resource.error(
                    getString(R.string.error_occurated),
                    false
            )))
        }
    }

    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {
        // when user pause player this would be called or resumed
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            _playBackState.postValue(state)
        }
        // skipped to new song
        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            _currentlyPlayingSong.postValue(metadata)
        }
        // sent custom event's from service to connection callback
        override fun onSessionEvent(event: String?, extras: Bundle?) {
            when (event) {
                NETWORK_ERROR -> _networkError.postValue(Event(Resource.error(getString(R.string.network_error), null)))
            }
        }

        // if session destroyed and we POST status in LiveData
        override fun onSessionDestroyed() {
            mediaBrowserConnectionCallback.onConnectionSuspended()
        }
    }
}