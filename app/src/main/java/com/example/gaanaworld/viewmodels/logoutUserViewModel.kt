package com.example.gaanaworld.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.gaanaworld.repository.UserAuthenticationRepository

class logoutUserViewModel(application: Application) : AndroidViewModel(application) {
    private val authenticationRepository = UserAuthenticationRepository()
    fun logoutUser() {
        authenticationRepository.logoutUser()
    }
    fun getAuthState() : MutableLiveData<Boolean> {
        return authenticationRepository.getUserAuthState()
    }
}