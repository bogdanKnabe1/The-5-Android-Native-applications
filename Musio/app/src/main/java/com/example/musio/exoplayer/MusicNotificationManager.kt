package com.example.musio.exoplayer

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import coil.imageLoader
import coil.request.ImageRequest

import com.example.musio.R
import com.example.musio.other.Constants.NOTIFICATION_CHANNEL_ID
import com.example.musio.other.Constants.NOTIFICATION_ID
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class MusicNotificationManager(
        private val context: Context,
        sessionToken: MediaSessionCompat.Token,
        notificationListener: PlayerNotificationManager.NotificationListener,
        private val newSongCallback: () -> Unit
) {

    private val notificationManager: PlayerNotificationManager

    init {
        val mediaController = MediaControllerCompat(context, sessionToken)
        notificationManager = PlayerNotificationManager.createWithNotificationChannel(
                context,
                NOTIFICATION_CHANNEL_ID,
                R.string.notification_channel_name,
                R.string.notification_channel_description,
                NOTIFICATION_ID,
                DescriptionAdapter(mediaController),
                notificationListener
        ).apply {
            setSmallIcon(R.drawable.ic_music)
            // special token with our media session to show player in notification
            // gives media controls such as play/pause/seek/skip
            setMediaSessionToken(sessionToken)
        }
    }

    fun showNotification(player: Player) {
        notificationManager.setPlayer(player)
    }

    private inner class DescriptionAdapter(
            private val mediaController: MediaControllerCompat
    ) : PlayerNotificationManager.MediaDescriptionAdapter {

        override fun getCurrentContentTitle(player: Player): CharSequence {
            return mediaController.metadata.description.title.toString()
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return mediaController.sessionActivity
        }

        override fun getCurrentContentText(player: Player): CharSequence? {
            return mediaController.metadata.description.subtitle.toString()
        }

        override fun getCurrentLargeIcon(player: Player, callback: PlayerNotificationManager.BitmapCallback): Bitmap? {
            // coil impl
            val imageRequest = ImageRequest.Builder(context)
                    .data(mediaController.metadata.description.iconUri)
                    .target { result ->
                        callback.onBitmap((result as BitmapDrawable).bitmap)
                    }
                    .build()

            context.imageLoader.enqueue(imageRequest)

            return null
        }
    }
}