package com.example.gaanaworld.data.daos

import com.example.gaanaworld.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserDao {
    private var db = FirebaseFirestore.getInstance()

    suspend fun getUserDetails(UserUid:String):Boolean {
        return try {
            val result = db.collection("user")
                .whereEqualTo("ui",UserUid)
                .get().await()
            result != null
        } catch(e:Exception) {
            e.printStackTrace()
            false
        }
    }
    suspend fun saveUserDetails(user: User):Boolean {
        return try {
            var result = db.collection("user")
                .add(user).await()
            result != null
        }
        catch(e:Exception) {
            e.printStackTrace()
            false
        }
    }
}