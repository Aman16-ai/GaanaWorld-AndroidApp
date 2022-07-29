package com.example.gaanaworld.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gaanaworld.data.daos.SingerDao
import com.example.gaanaworld.data.daos.UserDao
import com.example.gaanaworld.data.model.Singers
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class ChooseSingersViewModel(application: Application) : AndroidViewModel(application) {

    private var singersListLiveData : MutableLiveData<List<Singers>> = MutableLiveData()
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
    fun saveUserSingers(singers : MutableList<Singers>) {
        viewModelScope.launch {
            _navStatus.value = userDao.saveUserSingers(singers)
        }
    }
    fun getAllSingers() : MutableLiveData<List<Singers>> {
        fetchAllSingers()
        return singersListLiveData
    }
    fun fetchAllSingers() {
        viewModelScope.launch {
            singersListLiveData.value = singerDao.getSingers()
        }
    }
}