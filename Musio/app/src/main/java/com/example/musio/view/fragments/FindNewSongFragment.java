package com.example.musio.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindNewSongFragment extends Fragment {

    public FindNewSongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_new_song, container, false); // Inflate the layout for this fragment


        return v;
    }
}
