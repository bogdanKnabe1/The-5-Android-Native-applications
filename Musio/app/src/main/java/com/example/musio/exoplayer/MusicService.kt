package com.example.musio.exoplayer

import android.app.PendingIntent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.example.musio.di.DependencyModule
import com.example.musio.exoplayer.callbacks.MusicPlaybackPreparer
import com.example.musio.exoplayer.callbacks.MusicPlayerEventListener
import com.example.musio.exoplayer.callbacks.MusicPlayerNotificationListener
import com.example.musio.other.Constants.SERVICE_TAG
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

// Service with music which would playing in foreground
class MusicService : MediaBrowserServiceCompat() {

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    private lateinit var musicNotificationManager: MusicNotificationManager
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var mediaSessionConnector: MediaSessionConnector

    lateinit var exoPlayer: SimpleExoPlayer
    lateinit var firebaseMusicSource: FirebaseMusicSource
    lateinit var dataSourceFactory: DefaultDataSourceFactory

    var isForegroundService = false

    private var currentPlayingSong: MediaMetadataCompat? = null

    override fun onCreate() {
        super.onCreate()

        //intent to our activity
        val activityIntent = packageManager?.getLaunchIntentForPackage(packageName).let {
            PendingIntent.getActivity(this, 0, it, 0)
        }

        exoPlayer = DependencyModule.provideExoPlayer(this, DependencyModule.provideAudioAttributes())
        firebaseMusicSource = DependencyModule.provideFirebaseMusicSource()
        dataSourceFactory = DependencyModule.provideDataSourceFactory(this)

        // token to get information about media session
        mediaSession = MediaSessionCompat(this, SERVICE_TAG).apply {
            setSessionActivity(activityIntent)
            isActive = true
        }

        sessionToken = mediaSession.sessionToken

        musicNotificationManager = MusicNotificationManager(
                this,
                mediaSession.sessionToken,
                MusicPlayerNotificationListener(this)
        ) {
            // lambda
        }

        val musicPlaybackPreparer = MusicPlaybackPreparer(firebaseMusicSource) {
            // lambda block will called every time, when user choose a new song
            currentPlayingSong = it
            preparedPlayer(
                    firebaseMusicSource.songs,
                    it,
                    true
            )
        }

        mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setPlaybackPreparer(musicPlaybackPreparer)
        mediaSessionConnector.setPlayer(exoPlayer)

        // set listener
        exoPlayer.addListener(MusicPlayerEventListener(this))
        musicNotificationManager.showNotification(exoPlayer)
    }

    private fun preparedPlayer(
            songs: List<MediaMetadataCompat>,
            itemToPlay: MediaMetadataCompat?,
            playNow: Boolean
    ) {
        val currentSongIndex = if (currentPlayingSong == null) 0 else songs.indexOf(itemToPlay)
        exoPlayer.prepare(firebaseMusicSource.asMediaSource(dataSourceFactory))
        exoPlayer.seekTo(currentSongIndex, 0L)
        exoPlayer.playWhenReady = playNow
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        TODO("Not yet implemented")
    }

    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaBrowserCompat.MediaItem>>) {
        TODO("Not yet implemented")
    }


    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}