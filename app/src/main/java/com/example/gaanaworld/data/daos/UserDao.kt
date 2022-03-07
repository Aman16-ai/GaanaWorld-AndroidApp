package com.example.gaanaworld.data.daos

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
    suspend fun  getUserDetails() : String {
        val result = db.collection("user").get().await()
        for ( i in result) {
            if(i.get("uid") == mAuth.currentUser?.uid) {
                return i.id
            }
        }
        return "nouser"
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

    suspend fun saveUserSingers(singers:MutableList<QueryDocumentSnapshot>): Boolean {
        return try {
            val userid = getUserDetails()
            if(userid != "nouser") {
                db.collection("user").document(userid).update("singers",singers).await()
                true
            } else {
                false
            }
        }catch(e : Exception) {
            e.printStackTrace()
            false
        }
    }
}