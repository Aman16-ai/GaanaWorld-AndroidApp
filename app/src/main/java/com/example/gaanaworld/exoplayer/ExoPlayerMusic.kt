package com.example.gaanaworld.exoplayer

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gaanaworld.data.model.Song
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import java.time.Duration
import kotlin.properties.Delegates

class ExoPlayerMusic(val context:Context) {

    private val exoPlayer = ExoPlayer.Builder(context).build()
    private var songDuration:MutableLiveData<Long> = MutableLiveData()

    public fun initializedPlayer(url:String) {
        exoPlayer.apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(url))
            setMediaItem(mediaItem)

            prepare()
            playWhenReady = true

        }

        exoPlayer.addListener(object:Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if(playbackState == ExoPlayer.STATE_READY) {
                    songDuration.value = exoPlayer.contentDuration

                }

            }
        })
    }

    public fun playPauseMusic() {
        if(exoPlayer.isPlaying) {
            exoPlayer.pause()
        }
        else {
            exoPlayer.play()
        }
    }
    public fun getMusicDuration() :MutableLiveData<Long> {
        return songDuration
    }

    fun seekToCurrentDuration(duration: Long) {
        exoPlayer.seekTo(duration)
    }
    public fun getCurrentPlayerPosition() : Long {
        return exoPlayer.currentPosition
    }


}