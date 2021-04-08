package com.example.musio.ui.fragment

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.view.*
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import com.example.musio.R
import com.example.musio.data.entity.Song
import com.example.musio.databinding.FragmentSongPlayerBinding
import com.example.musio.di.DependencyModule
import com.example.musio.exoplayer.toSong
import com.example.musio.other.Status.SUCCESS
import com.example.musio.other.isPlaying
import com.example.musio.ui.viewmodel.MainViewModel
import com.example.musio.ui.viewmodel.SongViewModel
import com.example.musio.ui.viewmodel.factory.ViewModelSongFactory
import java.text.SimpleDateFormat
import java.util.*

class SongFragment : Fragment() {
    private var songFragmentBinding: FragmentSongPlayerBinding? = null

    private val songBinding get() = songFragmentBinding!!
    private lateinit var mainViewModel: MainViewModel

    private val songViewModel: SongViewModel by activityViewModels {
        val application = requireActivity()
        ViewModelSongFactory(DependencyModule.provideMusicServiceConnection(application))
    }

    private var currentlyPlayingSong: Song? = null

    private var playbackStateCompat: PlaybackStateCompat? = null
    private var shouldUpdateSeekBar: Boolean = true

    // passed arguments from recycler view
    lateinit var title: String
    lateinit var subTitle: String
    lateinit var image: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        songFragmentBinding = FragmentSongPlayerBinding.inflate(inflater, container, false)
        val view = songBinding.root
        setUpStatusBar()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        subscribeToObservers()

        songBinding.playPauseBtn.setOnClickListener {
            currentlyPlayingSong?.let {
                mainViewModel.playOrToggleSong(it, true)
            }
        }

        songBinding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    setCurrentPlayerTimeToTextView(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                shouldUpdateSeekBar = false
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    mainViewModel.seekTo(it.progress.toLong())
                    shouldUpdateSeekBar = true
                }
            }

        })

        songBinding.prevButton.setOnClickListener {
            mainViewModel.skipToPreviousSong()
        }

        songBinding.nextButton.setOnClickListener {
            mainViewModel.skipToNextSong()
        }
    }

    /*private fun updateSongInfo() {
        title = requireArguments().getString("title").toString()
        subTitle = requireArguments().getString("subtitle").toString()
        image = requireArguments().getString("image").toString()
        position = requireArguments().getInt("position")
    }
*/
    private fun updateSongTitleAndImage(song: Song) {
        title = song.title
        subTitle = song.subTitle
        image = song.imageUrl
        setUpView()
    }

    private fun setUpView() {
        with(songBinding) {
            artistName.text = title
            musicName.text = subTitle
            roundRectCornerImageView.load(image) {
                crossfade(700)
                crossfade(true)
                transformations((CircleCropTransformation()))
            }
            imageViewBackground.load(image) {
                crossfade(true)
                crossfade(700)
                transformations(BlurTransformation(requireActivity(), 8f))
            }
        }
    }

    private fun setUpStatusBar() {
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(requireActivity(), WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(requireActivity(), WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            requireActivity().window.statusBarColor = Color.TRANSPARENT

        }
    }

    private fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win: Window = activity.window
        val winParams: WindowManager.LayoutParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    private fun subscribeToObservers() {
        mainViewModel.mediaItems.observe(viewLifecycleOwner) {
            it?.let { result ->
                when (result.status) {
                    SUCCESS -> {
                        result.data?.let { songs ->
                            if (currentlyPlayingSong == null && songs.isNotEmpty()) {
                                currentlyPlayingSong = songs[0]
                                updateSongTitleAndImage(songs[0])
                            }
                        }
                    }
                    else -> Unit
                }
            }
        }

        mainViewModel.currentlyPlayingSong.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            currentlyPlayingSong = it.toSong()
            updateSongTitleAndImage(currentlyPlayingSong!!)
        }

        mainViewModel.playBackState.observe(viewLifecycleOwner) {
            playbackStateCompat = it
            songBinding.playPauseBtn.setImageResource(
                    if (playbackStateCompat?.isPlaying == true) R.drawable.ic_pause_circle else R.drawable.ic_play_circle
            )
            songBinding.seekbar.progress = it?.position?.toInt() ?: 0
        }
        songViewModel.currentPlayerPosition.observe(viewLifecycleOwner) {
            if (shouldUpdateSeekBar) {
                songBinding.seekbar.progress = it.toInt()
                setCurrentPlayerTimeToTextView(it)
            }
        }
        songViewModel.currentSongDuration.observe(viewLifecycleOwner) {
            songBinding.seekbar.max = it.toInt()
            val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
            songBinding.songDuration.text = dateFormat.format(it)
        }
    }

    private fun setCurrentPlayerTimeToTextView(ms: Long) {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        songBinding.currentTime.text = dateFormat.format(ms)
    }
}