package com.example.musio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musio.exoplayer.MusicService
import com.example.musio.exoplayer.MusicServiceConnection
import com.example.musio.other.Constants.UPDATE_PLAYER_POSITION_INTERVAL
import com.example.musio.other.currentPlaybackPosition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SongViewModel(private val musicServiceConnection: MusicServiceConnection) : ViewModel() {

    private val playbackState = musicServiceConnection.playBackState

    private val _currentSongDuration = MutableLiveData<Long>()
    val currentSongDuration: LiveData<Long> = _currentSongDuration

    private val _currentPlayerPosition = MutableLiveData<Long>()
    val currentPlayerPosition: LiveData<Long> = _currentPlayerPosition

    init {
        updateCurrentPlayerPosition()
    }

    private fun updateCurrentPlayerPosition() {
        viewModelScope.launch {
            while(true) {
                val pos = playbackState.value?.currentPlaybackPosition
                if(currentPlayerPosition.value != pos) {
                    _currentPlayerPosition.postValue(pos!!) // check null
                    _currentSongDuration.postValue(MusicService.currentSongDuration)
                }
                delay(UPDATE_PLAYER_POSITION_INTERVAL)
            }
        }
    }
}