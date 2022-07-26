package com.example.gaanaworld.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.gaanaworld.data.repository.UserAuthenticationRepository

class logoutUserViewModel(application: Application) : AndroidViewModel(application) {
    private val authenticationRepository = UserAuthenticationRepository()
    fun logoutUser() {
        authenticationRepository.logoutUser()
    }
    fun getAuthState() : MutableLiveData<Boolean> {
        return authenticationRepository.getUserAuthState()
    }
}