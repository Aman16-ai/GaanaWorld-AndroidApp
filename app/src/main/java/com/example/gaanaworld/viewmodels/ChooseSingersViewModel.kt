package com.example.gaanaworld.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gaanaworld.data.daos.SingerDao
import com.google.firebase.firestore.QuerySnapshot
import com.google.firestore.admin.v1.Index
import kotlinx.coroutines.launch

class ChooseSingersViewModel(application: Application) : AndroidViewModel(application) {

    private var singersListLiveData : MutableLiveData<QuerySnapshot> = MutableLiveData()
    private var singerDao = SingerDao()
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