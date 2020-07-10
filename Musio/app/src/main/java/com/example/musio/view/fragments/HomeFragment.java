package com.example.musio.view.fragments;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musio.R;
import com.example.musio.adapter.AdapterHome;
import com.example.musio.adapter.AdapterPlaylist;
import com.example.musio.models.ModelPlaylist;
import com.example.musio.models.ModelSongList;
import com.example.musio.models.PlaylistItems;
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
import static androidx.constraintlayout.widget.Constraints.TAG;
/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private AdapterPlaylist adapter;
    private AdapterHome adapterCharts;
    private LinearLayoutManager managerHorizontal;
    private LinearLayoutManager managerVertical;
    private List<PlaylistItems> videoList = new ArrayList<>();
    private List<VideoYT> videoListCharts = new ArrayList<>();
    private ShimmerFrameLayout loading1,loading2;
    private boolean isScroll = false;
    private int currentItem, totalItem, scrollOutItem;
    private String nextPageToken = "";
    private Toolbar toolbarHome;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView menuBurgerButtonHome;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false); // Inflate the layout for this fragment

        //Inside onCreateView fragment
        // Inflate/find views in layout for this fragment
        loading1 = v.findViewById(R.id.shimmer1);
        loading2 = v.findViewById(R.id.shimmer2);
        RecyclerView rv = v.findViewById(R.id.recycler_playlist);
        RecyclerView rvCharts = v.findViewById(R.id.recycler_charts);
        menuBurgerButtonHome = v.findViewById(R.id.menu_burger_button_home);

        //toolbar
        toolbarHome = v.findViewById(R.id.toolbar_home_player);
        ((AppCompatActivity)requireActivity()).setSupportActionBar(toolbarHome);
        //set title to false toolbar settings
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);

        //drawer
        drawerLayout = requireActivity().findViewById(R.id.drawerCore);
        navigationView = requireActivity().findViewById(R.id.drawer_navigation_item);

        //set adapter's and managers
        adapter = new AdapterPlaylist(getContext(), videoList);
        adapterCharts = new AdapterHome(getContext(), videoListCharts);
        managerHorizontal = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        managerVertical = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        //set clickListener for burger button in navView
        menuBurgerButtonHome.setOnClickListener(v1 -> drawerLayout.openDrawer(navigationView));

        //charts
        rvCharts.setAdapter(adapterCharts);
        rvCharts.setLayoutManager(managerVertical);
        //disable RecyclerView scrolling
        rvCharts.setNestedScrollingEnabled(false);
        //infinite scroll
        rvCharts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScroll = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = managerVertical.getChildCount();
                totalItem = managerVertical.getItemCount();
                scrollOutItem = managerVertical.findFirstVisibleItemPosition();
                if (isScroll && (currentItem + scrollOutItem == totalItem)){
                    isScroll = false;
                    getJsonCharts();
                }
            }
        });

        //playlist
        rv.setAdapter(adapter);
        rv.setLayoutManager(managerHorizontal);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScroll = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = managerHorizontal.getChildCount();
                totalItem = managerHorizontal.getItemCount();
                scrollOutItem = managerHorizontal.findFirstVisibleItemPosition();
                if (isScroll && (currentItem + scrollOutItem == totalItem)){
                    isScroll = false;
                    getJson();
                }
            }
        });

        if (videoList.size() == 0){
            getJson();
        }

        if (videoList.size() == 0){
            getJsonCharts();
        }
        return v;
    }


    private void getJson() {
        loading1.setVisibility(View.VISIBLE);
        String url = YoutubeAPI.BASE_URL + YoutubeAPI.ply + YoutubeAPI.KEY + YoutubeAPI.part_ply + YoutubeAPI.chid;

        if (!nextPageToken.equals("")){
            url = url + YoutubeAPI.NPT + nextPageToken;
            loading1.setVisibility(View.GONE);
            loading2.setVisibility(View.VISIBLE);
        }

        if (nextPageToken == null){
            return;
        }

        Call<ModelPlaylist> data = YoutubeAPI.getVideo().getPlaylist(url);
        data.enqueue(new Callback<ModelPlaylist>() {
            @Override
            public void onResponse(@NotNull Call<ModelPlaylist> call, @NotNull Response<ModelPlaylist> response) {
                if (response.errorBody() != null){
                    Log.w(TAG, "onResponse: "+ response.errorBody() );
                    loading1.setVisibility(View.GONE);
                    loading2.setVisibility(View.GONE);
                } else {
                    ModelPlaylist mp = response.body();
                    videoList.addAll(Objects.requireNonNull(mp).getItems());
                    adapter.notifyDataSetChanged();
                    loading1.setVisibility(View.GONE);
                    loading2.setVisibility(View.GONE);
                    if (mp.getNextPageToken() != null){
                        nextPageToken = mp.getNextPageToken();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ModelPlaylist> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure playlist: ", t);
                loading1.setVisibility(View.GONE);
                loading2.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getJsonCharts() {
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
                    Log.w(ContentValues.TAG, "onResponse: " + response.errorBody() );
                    loading1.setVisibility(View.GONE);
                    loading2.setVisibility(View.GONE);
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    ModelSongList msl = response.body();
                    videoListCharts.addAll(Objects.requireNonNull(msl).getItems());
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
                Log.e(ContentValues.TAG, "onFailure: ", t);
                loading1.setVisibility(View.GONE);
                loading2.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
