package com.example.gaanaworld.exoplayer

import android.content.Context
import android.media.AudioAttributes
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.hls.DefaultHlsDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MusicService(var context:Context, var audioAudioAttributes:AudioAttributes) : MediaBrowserServiceCompat() {

    var dataMediaSourceFactory : DefaultDataSourceFactory = DefaultDataSourceFactory(context,Util.getUserAgent(context,"GaanaWorld"))

    var exoPlayer:SimpleExoPlayer = SimpleExoPlayer.Builder(context).build().apply{
        setAudioAttributes(audioAttributes,true)
        setHandleAudioBecomingNoisy(true)
    }
    private val serviceJob = Job()
    private val serivceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    private lateinit var mediaSession: MediaSessionCompat

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        TODO("Not yet implemented")
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        TODO("Not yet implemented")
    }

}