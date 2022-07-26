package com.example.gaanaworld.data.daos

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.gaanaworld.data.model.Song
import com.example.gaanaworld.helper.getFileExtension
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

class SongDao {

    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()
//    private val storageRef : StorageReference = FirebaseStorage.getInstance().reference

    suspend fun getAllSongs() : MutableList<Song> {
        return db.collection("songs").get().await().toObjects(Song::class.java)
    }

     private suspend fun upload(storageRef:StorageReference,fileUri:Uri): String? {
      val result = storageRef.putFile(fileUri).await()
         return if (result.task.isSuccessful) {
             val uri = storageRef.downloadUrl.await()
             return uri.toString()
         }
         else null
    }
    suspend fun uploadSongFile(song: Uri,type:String):String? {
        val storageRef = FirebaseStorage.getInstance().reference.child("Songs/").child("${System.currentTimeMillis()}.${type}")
        return upload(storageRef,song)
    }

    suspend fun uploadThumbnailFile(thumbnail: Uri,type:String):String? {
        val storageRef = FirebaseStorage.getInstance().reference.child("Thumbnail/").child("${System.currentTimeMillis()}.${type}")
        return upload(storageRef,thumbnail)
    }

    suspend fun uploadSongModel(song:Song) {
        db.collection("songs").add(song).await()
    }
}
