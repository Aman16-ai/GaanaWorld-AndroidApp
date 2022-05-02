package com.example.gaanaworld.data.daos

import androidx.lifecycle.MutableLiveData
import com.example.gaanaworld.data.model.Song
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class SongDao {

    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun getAllSongs() : MutableList<Song> {
        return db.collection("songs").get().await().toObjects(Song::class.java)
    }
}