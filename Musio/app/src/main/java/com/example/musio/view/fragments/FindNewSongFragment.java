package com.example.musio.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.musio.R;
import com.example.musio.adapter.AlbumAdapter;
import com.example.musio.adapter.ArtistAdapter;
import com.example.musio.adapter.TrackAdapter;
import com.example.musio.models.deezerData.Album;
import com.example.musio.models.deezerData.DataSearchAlbum;
import com.example.musio.models.deezerData.DataSearchArtist;
import com.example.musio.models.deezerData.DataSearchTrack;
import com.example.musio.models.deezerData.Track;
import com.example.musio.network.DeezerService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindNewSongFragment extends Fragment  {

    private RecyclerView recyclerViewArtist;
    private RecyclerView recyclerViewAlbum;
    private RecyclerView recyclerViewTrack;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManagerAlbum;
    private RecyclerView.LayoutManager layoutManagerTrack;
    private ProgressBar progressBar;
    private Toolbar toolbarSearch;
    private LinearLayout linearLayout;
    private ConstraintLayout constraintLayoutRecyclerArtist;
    private ConstraintLayout constraintLayoutRecyclerAlbum;
    private ConstraintLayout constraintLayoutRecyclerTrack;
    private ConstraintLayout constraintLayoutEmpty;
    public Album albumObj;

    public FindNewSongFragment() {
        // Required empty public constructor
    }

    //For fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_new_song, container, false); // Inflate the layout for this fragment

        //Setup toolbar for fragment and example for future
        toolbarSearch = v.findViewById(R.id.toolbar2);
        ((AppCompatActivity)requireActivity()).setSupportActionBar(toolbarSearch);
        toolbarSearch.setTitleTextAppearance(requireActivity(), R.style.NunitoExtraBold);

        //Find include's
        linearLayout = v.findViewById(R.id.support_layout);
        constraintLayoutRecyclerArtist = linearLayout.findViewById(R.id.main_search_recycler);
        constraintLayoutRecyclerAlbum = linearLayout.findViewById(R.id.album_search_recycler);
        constraintLayoutRecyclerTrack = linearLayout.findViewById(R.id.track_search_recycler);
        constraintLayoutEmpty = linearLayout.findViewById(R.id.main_empty_view);

        //Find recycleView for artist
        recyclerViewArtist = v.findViewById(R.id.author_recycler_view);
        recyclerViewArtist.setHasFixedSize(true);

        //Find recyclerView for album
        recyclerViewAlbum = v.findViewById(R.id.album_recycler_view);
        recyclerViewAlbum.setHasFixedSize(true);

        //Find recyclerView for track
        recyclerViewTrack = v.findViewById(R.id.track_recycler_view);
        recyclerViewTrack.setHasFixedSize(true);

        // use a linear layout manager for Artist recycler
        layoutManager = new LinearLayoutManager(requireActivity());
        recyclerViewArtist.setLayoutManager(layoutManager);

        // use a linear layout manager for Album recycler
        layoutManagerAlbum = new LinearLayoutManager(requireActivity());
        recyclerViewAlbum.setLayoutManager(layoutManagerAlbum);

        // use a linear layout manager for Track recycler
        layoutManagerTrack = new LinearLayoutManager(requireActivity());
        recyclerViewTrack.setLayoutManager(layoutManagerTrack);

        progressBar = v.findViewById(R.id.progress_circular);
        hideProgress();
        return v;
    }


    //Inflate menu
    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            String lastText = null;

            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!query.equals(lastText)) {
                    lastText = query;
                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                            "Search " + query,
                            Snackbar.LENGTH_SHORT).show();
                    showProgress();

                    Response.Listener<DataSearchArtist> rep = response -> {
                        Log.d(TAG, "searchAuthor Found " + response.getTotal() + " artist");
                        ArtistAdapter mAdapter = new ArtistAdapter(response.getData());
                        recyclerViewArtist.setAdapter(mAdapter);
                        mAdapter.setListener(artist -> {
                            constraintLayoutEmpty.setVisibility(View.GONE);
                            constraintLayoutRecyclerAlbum.setVisibility(View.VISIBLE);
                            searchAlbum(artist);
                        });
                        hideProgress();
                        constraintLayoutRecyclerArtist.setVisibility(View.VISIBLE);
                    };
                    final Response.ErrorListener error = error1 -> {
                        Log.e(TAG, "searchAuthor onErrorResponse: " + error1.getMessage());
                        hideProgress();
                        constraintLayoutEmpty.setVisibility(View.GONE);
                    };
                    //Call for artist
                    DeezerService.searchAuthor(requireActivity(), query, rep, error);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
    }


    //Method for call for album
    private void searchAlbum(String artist){
        Response.Listener<DataSearchAlbum> rep = response -> {
            Log.d(TAG, "searchAlbum Found " + response.getTotal() + " album");
            AlbumAdapter mAdapter = new AlbumAdapter(response.getData());
            mAdapter.setListener(album -> {
                constraintLayoutEmpty.setVisibility(View.GONE);
                constraintLayoutRecyclerArtist.setVisibility(View.GONE);
                constraintLayoutRecyclerAlbum.setVisibility(View.GONE);
                constraintLayoutRecyclerTrack.setVisibility(View.VISIBLE);
                searchTrack(album);
            });

            mAdapter.setListenerAlbumTransfer(album -> albumObj = album);
            recyclerViewArtist.setAdapter(mAdapter);
            hideProgress();
        };
        final Response.ErrorListener error = error1 -> {
            Log.e(TAG, "searchAlbum onErrorResponse: " + error1.getMessage());
            hideProgress();
        };

        //Fragment communication, pass artist
        DeezerService.searchAlbum(requireActivity(), artist, rep, error);
    }

    //Method for call for track
    private void searchTrack(int album){
        /*
        Create list tracks
        */
        Response.Listener<DataSearchTrack> rep = response -> {
            Log.d(TAG, "searchTrack Found " + response.getTotal() + " track");
            TrackAdapter mAdapter = new TrackAdapter(response.getData());
            mAdapter.setListener(track -> goToMusicPlayer(track, albumObj));
            recyclerViewTrack.setAdapter(mAdapter);
            hideProgress();
        };
        final Response.ErrorListener error = error1 -> {
            Log.e(TAG, "searchTrack onErrorResponse: " + error1.getMessage());
            hideProgress();
        };

        //Fragment communication, pass artist
        DeezerService.searchAlbumTrack(requireActivity(), album, rep, error);
    }

    //private method for call Music player
    private void goToMusicPlayer(Track track, Album album){

        Bundle bundle = new Bundle();
        bundle.putParcelable("key", track);
        bundle.putParcelable("keyAlbum", album);

        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.nav_player);

        MusicPlayerFragment musicPlayerFragment = new MusicPlayerFragment();
        musicPlayerFragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(((ViewGroup) requireView().getParent()).getId(), musicPlayerFragment, "musicPlayerFragment")
                .addToBackStack(null)
                .commit();

    }

    //ProgressBar
    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}
