package com.example.musio.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musio.exoplayer.MusicServiceConnection
import com.example.musio.ui.viewmodel.SongViewModel

class ViewModelSongFactory(private val musicServiceConnection: MusicServiceConnection) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SongViewModel(musicServiceConnection) as T
    }
}