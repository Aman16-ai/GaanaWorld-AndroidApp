package com.example.gaanaworld.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gaanaworld.data.daos.UserDao
import com.example.gaanaworld.data.model.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.lang.reflect.Executable

class UserAuthenticationRepository {
    private var mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private var userAuthState : MutableLiveData<Boolean> = MutableLiveData()
    private var userdoa : UserDao = UserDao()
    init {
        userAuthState.value = mAuth.currentUser != null
    }
    suspend fun registerUser(firstName:String,lastName:String,email:String,password:String) {
        try {
            val result:AuthResult = mAuth.createUserWithEmailAndPassword(email,password)
                .await()
            if(result.user != null) {
                val user = User(uid = mAuth.uid,firstName = firstName,lastName = lastName,email = email)
                val fireStoreResult :Boolean = userdoa.saveUserDetails(user)
                userAuthState.value = fireStoreResult
            }
            else {
                userAuthState.value = false
            }
        }catch (e:Exception) {
            e.printStackTrace()
        }
    }
    suspend fun loginUser(email:String,password: String) {
        try {
            val result : AuthResult = mAuth.signInWithEmailAndPassword(email,password).await()
            userAuthState.value = result.user != null
        }catch (e:Exception) {
            e.printStackTrace()
        }
    }
    fun logoutUser() {
        mAuth.signOut()
        userAuthState.value = false
    }
    fun getUserAuthState() :MutableLiveData<Boolean> {
        return userAuthState
    }
}