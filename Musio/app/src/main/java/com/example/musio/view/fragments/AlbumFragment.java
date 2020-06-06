package com.example.musio.view.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.musio.R;
import com.example.musio.adapter.SongListAdapter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<String> musics;
    private SongListAdapter songListAdapter;
    private String[] songs;

    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_albums, container, false); // Inflate the layout for this fragment

        //init
        recyclerView = v.findViewById(R.id.musicRecyclerView);

        askStoragePermissions();

        return v;
    }

    private ArrayList<File> findMusics(File file){

        ArrayList<File> musicLists = new ArrayList<File>();

        File[] files = file.listFiles();

        for(File singleFile: (Objects.requireNonNull(files))){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                musicLists.addAll(findMusics(singleFile));
            }else{
                if(singleFile.getName().endsWith(".mp3") ||
                        singleFile.getName().endsWith(".m4a") ||
                        singleFile.getName().endsWith(".wav") ||
                        singleFile.getName().endsWith(".m4b")) {

                    musicLists.add(singleFile);
                }
            }
        }
        return musicLists;
    }


    private void display(){

        final ArrayList<File> allSongs = findMusics(Environment.getExternalStorageDirectory());
        songs = new String[allSongs.size()];

        for(int i = 0; i < allSongs.size(); i++){
            songs[i] = allSongs.get(i).getName()
                    .replace(".mp3","")
                    .replace(".m4a","")
                    .replace(".wav","")
                    .replace(".m4b","");
        }

        List<String> songList = Arrays.asList(songs);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        songListAdapter = new SongListAdapter(requireActivity(), (ArrayList<String>) songList);
        recyclerView.setAdapter(songListAdapter);
    }

    /*@Override
    public void recyclerViewListClicked(View v, int position) {
        // start music player when song name is clicked
        String songName = songListAdapter.getItemAtPosition(position).toString();
        Intent play = new Intent(getActivity(), Player.class);
        play.putExtra("songs", allSongs).putExtra("songName",songName).putExtra("position",position);
        startActivity(play);
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted and now can proceed
                    display(); //a sample method called
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(requireActivity(), "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // add other cases for more permissions
        }
    }

    private void askStoragePermissions(){
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }

    //----------------------------------------------------------------------------------------------
    //EXAMPLE clean and fast handle for permission's with Dexter//
    /*public void askStoragePermissions(){
        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                display();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }*/

}
