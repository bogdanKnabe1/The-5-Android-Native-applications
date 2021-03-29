package com.example.musio.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musio.R
import com.example.musio.adapters.SongAdapter
import com.example.musio.databinding.FragmentHomeBinding
import com.example.musio.di.DependencyModule
import com.example.musio.other.Status
import com.example.musio.ui.viewmodel.MainViewModel
import com.example.musio.ui.viewmodel.ViewModelFactory

class HomeFragment : Fragment() {

    private var homeFragmentBinding: FragmentHomeBinding? = null

    private val mainViewModel: MainViewModel by activityViewModels {
        val application = requireActivity()
        ViewModelFactory(
                DependencyModule.provideMusicServiceConnection(application))
    }

    private lateinit var songAdapter: SongAdapter

    private val homeBinding get() = homeFragmentBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeFragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = homeBinding.root

        songAdapter = SongAdapter()
        setStatusBarColorDark()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // check ViewModelFactory // explicitly pass requireActivity() to get lifecycle of Main Activity
        setUpRecyclerView()
        subscribeToObservers()

        songAdapter.setOnItemClickListener {
            mainViewModel.playOrToggleSong(it)
        }
    }
    private fun setStatusBarColorDark() {
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
    private fun setUpRecyclerView() = homeBinding.rvAllSongs.apply {
        adapter = songAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun subscribeToObservers() {
        mainViewModel.mediaItems.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    homeBinding.allSongsProgressBar.isVisible = false
                    result.data?.let { songs ->
                        songAdapter.songs = songs // submit new value to list in adapter
                    }
                }
                Status.LOADING -> { homeBinding.allSongsProgressBar.isVisible = true }
                Status.ERROR -> Unit
            }
        }
    }
}