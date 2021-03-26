package com.example.musio.ui.viewmodel

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_ID
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musio.data.entity.Song
import com.example.musio.exoplayer.MusicServiceConnection
import com.example.musio.other.Constants.MEDIA_ROOT_ID
import com.example.musio.other.Resource
import com.example.musio.other.isPlayEnabled
import com.example.musio.other.isPlaying
import com.example.musio.other.isPrepared

class MainViewModel(private val musicServiceConnection: MusicServiceConnection) : ViewModel() {

    private val _mediaItems = MutableLiveData<Resource<List<Song>>>()
    val mediaItems: LiveData<Resource<List<Song>>> = _mediaItems

    val isConnected = musicServiceConnection.isConnected
    val networkError = musicServiceConnection.networkError
    val currentlyPlayingSong = musicServiceConnection.currentlyPlayingSong
    val playBackState = musicServiceConnection.playBackState

    init {
        _mediaItems.postValue(Resource.loading(null))
        musicServiceConnection.subscribe(MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {
            override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>) {
                // map MediaItem to Song object, that live data accept
                val items = children.map {
                    Song(
                            it.mediaId!!,
                            it.description.title.toString(),
                            it.description.subtitle.toString(),
                            it.description.mediaUri.toString(),
                            it.description.iconUri.toString()
                    )
                }
                _mediaItems.postValue(Resource.success(items))
            }
        })
    }

    fun skipToNextSong() = musicServiceConnection.transportControls.skipToNext()

    fun skipToPreviousSong() = musicServiceConnection.transportControls.skipToPrevious()

    fun seekTo(position: Long) = musicServiceConnection.transportControls.seekTo(position)

    fun playOrToggleSong(mediaItem: Song, toggle: Boolean = false) {
        val isPrepared = playBackState.value?.isPrepared ?: false
        if (isPrepared && mediaItem.mediaId == currentlyPlayingSong.value?.getString(METADATA_KEY_MEDIA_ID)) {
            playBackState.value?.let { playBackState ->
                when {
                    playBackState.isPlaying -> if (toggle) musicServiceConnection.transportControls.pause() // PAUSE
                    playBackState.isPlayEnabled -> musicServiceConnection.transportControls.play() // PLAY
                    else -> Unit
                }
            }
        } else {
            // we want to play a NEW song
            musicServiceConnection.transportControls.playFromMediaId(mediaItem.mediaId, null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        // clear all resources and un subscribe from MEDIA root id
        musicServiceConnection.unSubscribe(MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {})
    }
}