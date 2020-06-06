package com.example.musio.view.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musio.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicPlayerFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<String> musics;
    private ArrayAdapter<String> mArrayAdapter;
    private String[] songs;

    public MusicPlayerFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music_player, container, false); // Inflate the layout for this fragment

        //init
        recyclerView = v.findViewById(R.id.musicRecyclerView);

        askStoragePermissions();

        return v;
    }

    private void askStoragePermissions(){

    }

}
