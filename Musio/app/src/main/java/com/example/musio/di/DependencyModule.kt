package com.example.musio.di

import android.content.Context
import com.example.musio.data.remote.MusicDatabase
import com.example.musio.exoplayer.FirebaseMusicSource
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

// check DI ( now service locator pattern )
// Now An alternative to dependency injection is using a 'service locator'
object DependencyModule {

    fun provideMusicDatabase() = MusicDatabase()

    fun provideFirebaseMusicSource() = FirebaseMusicSource(provideMusicDatabase())

    fun provideAudioAttributes() = AudioAttributes.Builder()
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()

    fun provideExoPlayer(
            context: Context,
            audioAttributes: AudioAttributes
    ) = SimpleExoPlayer.Builder(context).build().apply {
        setAudioAttributes(audioAttributes, true)
        setHandleAudioBecomingNoisy(true)
    }

    fun provideDataSourceFactory(context: Context) =
            DefaultDataSourceFactory(context, Util.getUserAgent(context, "Musio App"))

}