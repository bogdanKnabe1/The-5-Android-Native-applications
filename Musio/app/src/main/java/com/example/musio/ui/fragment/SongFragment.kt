package com.example.musio.ui.fragment

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import com.example.musio.databinding.FragmentSongPlayerBinding

class SongFragment : Fragment() {

    private var songFragmentBinding: FragmentSongPlayerBinding? = null

    private val songBinding get() = songFragmentBinding!!

    // passed arguments from recycler view
    lateinit var title: String
    lateinit var subTitle: String
    lateinit var image: String
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = requireArguments().getString("title").toString()
        subTitle = requireArguments().getString("subtitle").toString()
        image = requireArguments().getString("image").toString()
        position = requireArguments().getInt("position")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        songFragmentBinding = FragmentSongPlayerBinding.inflate(inflater, container, false)
        val view = songBinding.root
        setUpStatusBar()
        setUpView()
        return view
    }

    private fun setUpView() {
        with(songBinding) {
            artistName.text = title
            musicName.text = subTitle
            roundRectCornerImageView.load(image){
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
}