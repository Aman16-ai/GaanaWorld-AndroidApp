package com.example.gaanaworld.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gaanaworld.data.daos.SongDao
import com.example.gaanaworld.data.model.Song
import com.example.gaanaworld.exoplayer.ExoPlayerMusic
import kotlinx.coroutines.launch

class SongsViewModel(application: Application) : AndroidViewModel(application) {


    private var exoPlayerMusic = ExoPlayerMusic(application.applicationContext)
    private val songDao = SongDao()
    private var songslst : MutableLiveData<MutableList<Song>> = MutableLiveData()

    fun playSong(url:String) {
//        mediaPlayerService.playSound(url)
        exoPlayerMusic.playMusic(url)
    }


    fun getSongs() : MutableLiveData<MutableList<Song>> {
        fetchSongs()
        return songslst
    }
    fun fetchSongs() {
        viewModelScope.launch {
            songslst.value = songDao.getAllSongs()
        }
    }
}