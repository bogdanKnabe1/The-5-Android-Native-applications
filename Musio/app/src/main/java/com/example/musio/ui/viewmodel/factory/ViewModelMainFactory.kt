package com.example.musio.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musio.exoplayer.MusicServiceConnection
import com.example.musio.ui.viewmodel.MainViewModel

class ViewModelMainFactory(private val musicServiceConnection: MusicServiceConnection) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(musicServiceConnection) as T
    }
}