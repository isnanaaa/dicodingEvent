package com.example.dicodingevent.ui.fav

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dicodingevent.data.FavEvent
import com.example.dicodingevent.repository.FavRepo

class FavViewModel(application: Application) : AndroidViewModel(application) {
    private val _isLoad = MutableLiveData<Boolean>()
    val isLoad: LiveData<Boolean> get() = _isLoad

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val favRepo = FavRepo(application)

    private val _favEventt = MutableLiveData<List<FavEvent>>()
    val favEventt: LiveData<List<FavEvent>> get() = _favEventt

    init {
        fetchFavEvent()
    }

    private fun fetchFavEvent(){
        _isLoad.value = true
        favRepo.getAllFav().observeForever{ favs ->
            _favEventt.value = favs
        }
    }
}