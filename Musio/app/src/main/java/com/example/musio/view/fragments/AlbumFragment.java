
package com.example.musio.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musio.R;
import com.example.musio.adapter.AdapterHome;
import com.example.musio.models.ModelSongList;
import com.example.musio.models.VideoYT;
import com.example.musio.network.YoutubeAPI;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */

public class AlbumFragment extends Fragment {


    private AdapterHome adapter;
    private final List<VideoYT> videoList = new ArrayList<>();
    private ShimmerFrameLayout loading1,loading2;
    private boolean isScroll = false;
    private String nextPageToken = "";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // to display menu in action bar
        //setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_albums, container, false);

        //init view's in fragment
        TextView buttonBurgerMenu = v.findViewById(R.id.menu_burger_button);
        loading1 = v.findViewById(R.id.shimmer1);
        loading2 = v.findViewById(R.id.shimmer2);
        RecyclerView rv = v.findViewById(R.id.musicRecyclerView);

        //toolbar
        Toolbar toolbarMusicPlayer = v.findViewById(R.id.toolbar_music_player);
        ((AppCompatActivity)requireActivity()).setSupportActionBar(toolbarMusicPlayer);
        //set title to false toolbar settings
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);

        //drawer
        drawerLayout = requireActivity().findViewById(R.id.drawerCore);
        navigationView = requireActivity().findViewById(R.id.drawer_navigation_item);

        //set adapter + manager
        adapter = new AdapterHome(getContext(),videoList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv.setAdapter(adapter);
        rv.setLayoutManager(manager);

        if (videoList.size() == 0){
            getJson();
        }

        //set clickListener for burger button in navView
        buttonBurgerMenu.setOnClickListener(v1 -> drawerLayout.openDrawer(navigationView));

        return v;
    }



    private void getJson() {
        loading1.setVisibility(View.VISIBLE);
        String url = YoutubeAPI.BASE_URL + YoutubeAPI.sch + YoutubeAPI.KEY + YoutubeAPI.chid + YoutubeAPI.mx + YoutubeAPI.ord
                + YoutubeAPI.part;
        if (!nextPageToken.equals("")){
            url = url + YoutubeAPI.NPT + nextPageToken;
            loading1.setVisibility(View.GONE);
            loading2.setVisibility(View.VISIBLE);
        }
        if (nextPageToken == null){
            return;
        }
        Call<ModelSongList> data = YoutubeAPI.getVideo().getHomeVideo(url);
        data.enqueue(new Callback<ModelSongList>() {
            @Override
            public void onResponse(@NotNull Call<ModelSongList> call, @NotNull Response<ModelSongList> response) {
                if (response.errorBody() != null){
                    Log.w(TAG, "onResponse: " + response.errorBody() );
                    loading1.setVisibility(View.GONE);
                    loading2.setVisibility(View.GONE);
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    ModelSongList msl = response.body();
                    videoList.addAll(Objects.requireNonNull(msl).getItems());
                    adapter.notifyDataSetChanged();
                    loading1.setVisibility(View.GONE);
                    loading2.setVisibility(View.GONE);
                    if (msl.getNextPageToken() != null){
                        nextPageToken = msl.getNextPageToken();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ModelSongList> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                loading1.setVisibility(View.GONE);
                loading2.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}





