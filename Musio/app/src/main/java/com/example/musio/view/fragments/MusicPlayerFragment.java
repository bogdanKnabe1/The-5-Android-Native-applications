package com.example.musio.view.fragments;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.musio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicPlayerFragment extends Fragment {

    private ImageView songPreviewImage, playPauseBtn;
    private TextView textViewCurrentTime, textViewTotalTime;
    private SeekBar playerSeekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    public MusicPlayerFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music_player, container, false); // Inflate the layout for this fragment
        //init
        songPreviewImage = v.findViewById(R.id.song_image);
        playPauseBtn = v.findViewById(R.id.play_pause_btn);
        textViewCurrentTime = v.findViewById(R.id.current_time);
        textViewTotalTime = v.findViewById(R.id.total_time);
        playerSeekBar = v.findViewById(R.id.seekbar);
        mediaPlayer = new MediaPlayer();

        return v;
    }


}
