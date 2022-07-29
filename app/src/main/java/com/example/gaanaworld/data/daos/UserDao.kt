package com.example.gaanaworld.data.daos

import com.example.gaanaworld.data.model.Singers
import com.example.gaanaworld.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserDao {
    private var db = FirebaseFirestore.getInstance()
    private var mAuth = FirebaseAuth.getInstance()
    suspend fun  getUserDetails() {
        val result = mAuth.uid?.let { db.collection("user").document(it).get().await() }
    }
    suspend fun saveUserDetails(user: User):Boolean {
        return try {
            var result = mAuth.uid?.let {
                db.collection("user")
                    .document(it).set(user).await()
            }
            result != null
        }
        catch(e:Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun saveUserSingers(singers:MutableList<Singers>): Boolean {
        return try {
            val result = mAuth.uid?.let { db.collection("user").document(it).update("singers",singers).await() }
            return true

        }catch(e : Exception) {
            e.printStackTrace()
            false
        }
    }
}