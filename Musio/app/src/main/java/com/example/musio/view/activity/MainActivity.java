package com.example.musio.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.musio.R;
import com.example.musio.view.fragments.AlbumFragment;
import com.example.musio.view.fragments.MusicPlayerFragment;
import com.example.musio.view.fragments.FindNewSongFragment;
import com.example.musio.view.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private HomeFragment homeFragment = new HomeFragment();
    private AlbumFragment albumFragment = new AlbumFragment();
    private MusicPlayerFragment musicPlayerFragment = new MusicPlayerFragment();
    private FindNewSongFragment findNewSongFragment  = new FindNewSongFragment();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make fullscreen
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            // Do something for P and above versions

            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else{
            // do something for phones running an SDK before P
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }

        //INIT
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerCore);
        navigationView = findViewById(R.id.drawer_navigation_item);

        //set toolbar
        setSupportActionBar(toolbar);

        //toggle drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout,toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.getHeaderView(0).setOnClickListener(v -> drawerLayout.closeDrawer(Gravity.LEFT));

        //set icon and toolbar settings
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_burger);

        navigationView.setNavigationItemSelectedListener(this);


        //Navigation bottom menu
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);;
        setFragment(homeFragment);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            if (item.isChecked()){
                return true;
            } else {
                switch (item.getItemId()) {
                    case R.id.nav_album:
                        setFragment(albumFragment);
                        return true;
                    case R.id.nav_player:
                        setFragment(musicPlayerFragment);
                        return true;
                    case R.id.nav_find_song:
                        setFragment(findNewSongFragment);
                        return true;
                    case R.id.nav_homeProfile:
                    default:
                        setFragment(homeFragment);
                        return true;
                }
            }
        });

    }

    public void setFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    // Navigation drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.albumsId:
                Toast.makeText(MainActivity.this, "Albums is Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.songId:
                Toast.makeText(MainActivity.this, "Songs is Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.musicId:
                Toast.makeText(MainActivity.this, "Music is Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settingsId:
                Toast.makeText(MainActivity.this, "Settings is Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logoutID:
                //signOut();
                break;
            default:
                break;
        }
        return false;
    }


}
