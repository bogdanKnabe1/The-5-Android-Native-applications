package com.example.musio.view.fragments;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.example.musio.R;
import com.example.musio.adapter.AlbumAdapter;
import com.example.musio.adapter.ArtistAdapter;
import com.example.musio.models.deezerData.DataSearchAlbum;
import com.example.musio.models.deezerData.DataSearchArtist;
import com.example.musio.network.DeezerService;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindNewSongFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    private Toolbar toolbarSearch;
    private LinearLayout linearLayout;
    private ConstraintLayout constraintLayoutRecycler;
    private ConstraintLayout constraintLayoutEmpty;


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

        linearLayout = v.findViewById(R.id.support_layout);
        constraintLayoutRecycler = linearLayout.findViewById(R.id.main_search_recycler);
        constraintLayoutEmpty = linearLayout.findViewById(R.id.main_empty_view);

        progressBar = v.findViewById(R.id.progress_circular);
        hideProgress();

        recyclerView = v.findViewById(R.id.author_recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);


        return v;
    }

    //Inflate m
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
                        recyclerView.setAdapter(mAdapter);
                        hideProgress();
                        constraintLayoutRecycler.setVisibility(View.VISIBLE);
                    };
                    final Response.ErrorListener error = error1 -> {
                        Log.e(TAG, "searchAuthor onErrorResponse: " + error1.getMessage());
                        hideProgress();
                        constraintLayoutEmpty.setVisibility(View.GONE);
                    };

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

    public void searchAlbum(){
        Response.Listener<DataSearchAlbum> rep = response -> {
            Log.d(TAG, "searchAlbum Found " + response.getTotal() + " album");
            AlbumAdapter mAdapter = new AlbumAdapter(response.getData());
            recyclerView.setAdapter(mAdapter);
            hideProgress();
        };
        final Response.ErrorListener error = error1 -> {
            Log.e(TAG, "searchAlbum onErrorResponse: " + error1.getMessage());
            hideProgress();
        };

        //Fragment communication, pass artist
        //DeezerService.searchAlbum(requireActivity(), artist, rep, error);
    }


    //ProgressBar
    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}
