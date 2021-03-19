package com.example.musio.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.musio.R
import com.example.musio.data.remote.MusicDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var database: MusicDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = MusicDatabase()
        GlobalScope.launch {
            database.getAllSongs()
            Log.d("TAG", "WE GET IT")
        }
    }
}