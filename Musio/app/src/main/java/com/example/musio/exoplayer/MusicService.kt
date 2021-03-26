package com.example.musio.exoplayer

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.example.musio.di.DependencyModule
import com.example.musio.exoplayer.callbacks.MusicPlaybackPreparer
import com.example.musio.exoplayer.callbacks.MusicPlayerEventListener
import com.example.musio.exoplayer.callbacks.MusicPlayerNotificationListener
import com.example.musio.other.Constants.MEDIA_ROOT_ID
import com.example.musio.other.Constants.SERVICE_TAG
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.coroutines.*

// Service with music which would playing in foreground
class MusicService : MediaBrowserServiceCompat() {

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    private lateinit var musicNotificationManager: MusicNotificationManager
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var mediaSessionConnector: MediaSessionConnector
    private lateinit var musicPlayerEventListener: MusicPlayerEventListener

    lateinit var exoPlayer: SimpleExoPlayer
    lateinit var firebaseMusicSource: FirebaseMusicSource
    lateinit var dataSourceFactory: DefaultDataSourceFactory

    var isForegroundService = false
    private var isPlayerInitialized = false

    private var currentPlayingSong: MediaMetadataCompat? = null

    companion object {
        var currentSongDuration = 0L
            private set
    }

    override fun onCreate() {
        super.onCreate()
        exoPlayer = DependencyModule.provideExoPlayer(this, DependencyModule.provideAudioAttributes())
        firebaseMusicSource = DependencyModule.provideFirebaseMusicSource()
        dataSourceFactory = DependencyModule.provideDataSourceFactory(this)
        musicPlayerEventListener = MusicPlayerEventListener(this)

        serviceScope.launch {
            firebaseMusicSource.fetchMediaData()
        }

        //intent to our activity
        val activityIntent = packageManager?.getLaunchIntentForPackage(packageName).let {
            PendingIntent.getActivity(this, 0, it, 0)
        }

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
            currentSongDuration = exoPlayer.duration
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
        mediaSessionConnector.setQueueNavigator(MusicQueueNavigator())
        mediaSessionConnector.setPlayer(exoPlayer)

        // set listener
        exoPlayer.addListener(musicPlayerEventListener)
        musicNotificationManager.showNotification(exoPlayer)
    }

    private inner class MusicQueueNavigator() : TimelineQueueNavigator(mediaSession) {
        // called when our service need new description. When song changed, notification need to show new song description
        override fun getMediaDescription(player: Player, windowIndex: Int): MediaDescriptionCompat {
            return firebaseMusicSource.songs[windowIndex].description
        }
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

    // playlist, albums etc(all is it browserable)
    // if we need some verification to client connection to our bound service, we need do it in onGetRoot
    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        return BrowserRoot(MEDIA_ROOT_ID, null)
    }

    // each of playlist, albums, songs has their ID, so clients can 'subscribe' to mediaId
    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaBrowserCompat.MediaItem>>) {
        when (parentId) {
            MEDIA_ROOT_ID -> {
                val resultSent = firebaseMusicSource.whenReady { isInitialized ->
                    if (isInitialized) {
                        result.sendResult(firebaseMusicSource.asMediaItems())
                        // prevent auto play music, when app started
                        if (!isPlayerInitialized && firebaseMusicSource.songs.isNotEmpty()) {
                            preparedPlayer(firebaseMusicSource.songs, firebaseMusicSource.songs[0], false)
                            isPlayerInitialized = true
                        }
                    } else {
                        result.sendResult(null)
                    }
                }
                if (!resultSent) {
                    result.detach()
                }
            }
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        exoPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        exoPlayer.removeListener(musicPlayerEventListener)
        exoPlayer.release()
    }
}