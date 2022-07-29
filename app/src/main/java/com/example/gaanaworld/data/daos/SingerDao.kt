package com.example.gaanaworld.data.daos

import com.example.gaanaworld.data.model.Singers
import com.google.android.play.core.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
class SingerDao {

    private val db = FirebaseFirestore.getInstance()

    suspend fun getSingers() : List<Singers>{
       return db.collection("singers").get().await().toObjects(Singers::class.java)
    }
}