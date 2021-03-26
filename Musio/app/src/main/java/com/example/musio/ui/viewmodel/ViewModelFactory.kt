package com.example.musio.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musio.exoplayer.MusicServiceConnection

class ViewModelFactory(private val musicServiceConnection: MusicServiceConnection) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(musicServiceConnection) as T
    }
}