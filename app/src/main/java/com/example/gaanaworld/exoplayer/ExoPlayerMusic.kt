package com.example.gaanaworld.exoplayer

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util

class ExoPlayerMusic(val context:Context) {

    private val exoPlayer = SimpleExoPlayer.Builder(context).build()

    public fun playMusic(url:String) {
        val mediaSource = extractMediaSourceFromUri(url)
        exoPlayer.apply {
            val attr = AudioAttributes.Builder().setUsage(C.USAGE_MEDIA)
                .setContentType(C.CONTENT_TYPE_MUSIC)
                .build()

            setAudioAttributes(attr,true)
            prepare(mediaSource)
            playWhenReady = true
        }
    }

    private fun extractMediaSourceFromUri(uri:String):MediaSource {
        val userAgent = Util.getUserAgent(context,"Exo");
        return ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(MediaItem.fromUri(Uri.parse(uri)))
    }


}