package com.example.gaanaworld.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gaanaworld.data.daos.SingerDao
import com.example.gaanaworld.data.daos.UserDao
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class ChooseSingersViewModel(application: Application) : AndroidViewModel(application) {

    private var singersListLiveData : MutableLiveData<QuerySnapshot> = MutableLiveData()
    private var singerDao = SingerDao()
    private var userDao = UserDao()
    private var _navStatus : MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val navStatus : LiveData<Boolean>
    get()= _navStatus

    init {
        _navStatus.value = false

    }
    fun skipedSingers() {
        _navStatus.value = true
    }
    fun saveUserSingers(singers : MutableList<QueryDocumentSnapshot>) {
        viewModelScope.launch {
            _navStatus.value = userDao.saveUserSingers(singers)
        }
    }
    fun getAllSingers() : MutableLiveData<QuerySnapshot> {
        fetchAllSingers()
        return singersListLiveData
    }
    fun fetchAllSingers() {
        viewModelScope.launch {
            singersListLiveData.value = singerDao.getSingers()
        }
    }
}