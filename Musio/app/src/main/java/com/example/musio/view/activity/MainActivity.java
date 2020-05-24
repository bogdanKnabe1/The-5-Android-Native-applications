package com.example.musio.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.musio.R;
import com.example.musio.view.fragments.AlbumFragment;
import com.example.musio.view.fragments.FindNewSongFragment;
import com.example.musio.view.fragments.HomeFragment;
import com.example.musio.view.fragments.SongsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INIT
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerCore);
        navigationView = findViewById(R.id.drawer_navigation_item);

        //set toolbar
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //toggle drawer
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);

        //Navigation bottom menu
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

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


    //bottom navigation menu
    BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_homeProfile:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_albums:
                            selectedFragment = new AlbumFragment();
                            break;
                        case R.id.nav_songs:
                            selectedFragment = new SongsFragment();
                            break;
                        case R.id.nav_find_song:
                            selectedFragment = new FindNewSongFragment();
                            break;
                    }

                    if (selectedFragment != null) {
                        MainActivity.this.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                    }
                    return true;
                }
            };
}
