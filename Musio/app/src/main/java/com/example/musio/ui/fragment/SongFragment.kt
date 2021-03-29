package com.example.musio.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.musio.databinding.FragmentSongPlayerBinding

class SongFragment : Fragment() {

    private var songFragmentBinding: FragmentSongPlayerBinding? = null

    private val songBinding get() = songFragmentBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        songFragmentBinding = FragmentSongPlayerBinding.inflate(inflater, container, false)
        val view = songBinding.root

        return view
    }
}