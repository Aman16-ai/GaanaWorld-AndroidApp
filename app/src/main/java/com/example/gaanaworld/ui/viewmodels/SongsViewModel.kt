package com.example.gaanaworld.ui.viewmodels

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gaanaworld.data.daos.SongDao
import com.example.gaanaworld.data.model.Song
import com.example.gaanaworld.exoplayer.ExoPlayerMusic
import com.example.gaanaworld.helper.TaskProgressTracker
import com.example.gaanaworld.utils.toast
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SongsViewModel(application: Application) : AndroidViewModel(application) {


    private var exoPlayerMusic = ExoPlayerMusic(application.applicationContext)
    private val songDao = SongDao()
    private var songslst : MutableLiveData<MutableList<Song>> = MutableLiveData()
    private var _currentPosition : MutableLiveData<Long> = MutableLiveData()
    val currentPosition : LiveData<Long>
    get () = _currentPosition
    private var _uploadProgressTracker : MutableLiveData<TaskProgressTracker> = MutableLiveData()

    val uploadProgressTracker : LiveData<TaskProgressTracker>
    get() = _uploadProgressTracker

    init {
        getCurrentSongDuration()
    }
    fun initialized(url:String) {
        exoPlayerMusic.initializedPlayer(url)
    }

    fun playPauseSong() {
        exoPlayerMusic.playPauseMusic()
    }

    fun getSongs() : MutableLiveData<MutableList<Song>> {
        fetchSongs()
        return songslst
    }

    fun seekTo(duration:Long) {
        exoPlayerMusic.seekToCurrentDuration(duration)
    }
    fun getSongDuration() : MutableLiveData<Long> {
        return exoPlayerMusic.getMusicDuration()
    }
    private fun getCurrentSongDuration() {
        viewModelScope.launch {
            while (true) {
                _currentPosition.postValue(exoPlayerMusic.getCurrentPlayerPosition())
                delay(100L)
            }
        }
    }

    fun fetchSongs() {
        viewModelScope.launch {
            songslst.value = songDao.getAllSongs()
        }
    }

    fun uploadSongtoDB(name:String,language:String, thumbnailUri: Uri,songUri:Uri,songType:String,thumbnailType:String) {
        viewModelScope.launch {
          try {
              _uploadProgressTracker.postValue(TaskProgressTracker.Loading())
              val songUrl = async { songDao.uploadSongFile(songUri,songType) }
              val thumbnailUrl = async { songDao.uploadThumbnailFile(thumbnailUri,thumbnailType) }

              val song = thumbnailUrl.await()
                  ?.let { songUrl.await()
                      ?.let { it1 -> Song(name = name, language = language, thumbnail = it, url = it1) } }

              if (song != null) {
                  songDao.uploadSongModel(song)
              }
              _uploadProgressTracker.postValue(TaskProgressTracker.Done(message = "Uploaded successfully"))
          }
          catch(err:Error) {
              _uploadProgressTracker.postValue(TaskProgressTracker.Error(message = err.message.toString()))
          }
        }
    }

    public fun durationConverter(time:Int) : String {
        val min : Int = (time % (1000 * 60 * 60))/(1000 * 60);
        val sec : Int = (((time % (1000 * 60 * 60)) % (1000 * 60 * 60)) % (1000 * 60 * 60))/1000;
        val convertedTime : String = "$min:$sec"

        return convertedTime
    }
}