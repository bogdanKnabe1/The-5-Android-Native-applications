
package com.example.musio.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musio.R;
import com.example.musio.adapter.AdapterHome;
import com.example.musio.models.ModelSongList;
import com.example.musio.models.VideoYT;
import com.example.musio.network.YoutubeAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */

public class AlbumFragment extends Fragment {


    private AdapterHome adapter;
    private LinearLayoutManager manager;
    private List<VideoYT> videoList = new ArrayList<>();


    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // to display menu in action bar
        //setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_albums, container, false);

        RecyclerView rv = view.findViewById(R.id.musicListView);
        adapter = new AdapterHome(getContext(),videoList);
        manager = new LinearLayoutManager(getContext());
        rv.setAdapter(adapter);
        rv.setLayoutManager(manager);

        if (videoList.size() == 0){
            getJson();
        }

        return view;
    }

    private void getJson() {
        String url = YoutubeAPI.BASE_URL + YoutubeAPI.sch + YoutubeAPI.KEY + YoutubeAPI.chid + YoutubeAPI.mx + YoutubeAPI.ord
                + YoutubeAPI.part;

        Call<ModelSongList> data = YoutubeAPI.getVideo().getHomeVideo(url);
        data.enqueue(new Callback<ModelSongList>() {
            @Override
            public void onResponse(Call<ModelSongList> call, Response<ModelSongList> response) {
                if (response.errorBody() != null){
                    Log.w(TAG, "onResponse: " + response.errorBody() );
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    ModelSongList mh = response.body();
                    videoList.addAll(mh.getItems());
                    adapter.notifyDataSetChanged();

                    /*if (mh.getNextPageToken() != null){
                        nextPageToken = mh.getNextPageToken();
                    }*/
                }
            }

            @Override
            public void onFailure(Call<ModelSongList> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}





