package com.example.dicodingevent.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.dicodingevent.data.FavDao
import com.example.dicodingevent.data.FavEvent
import com.example.dicodingevent.data.FavRoomDB
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavRepo (application: Application){
    private val iFavDao: FavDao
    private val excutorService: ExecutorService = Executors.newSingleThreadScheduledExecutor()

    init {
        val db = FavRoomDB.getDb(application)
        iFavDao = db.favDao()
    }

    fun getAllFav(): LiveData<List<FavEvent>> = iFavDao.getAllFav()

    fun insert (favoriteEvent: FavEvent){
        excutorService.execute {iFavDao.insert(favoriteEvent)}
    }

    fun delete (favoriteEvent: FavEvent){
        excutorService.execute {iFavDao.delete(favoriteEvent)}
    }

    fun isFav(eventId: Int): LiveData<FavEvent?>{
        return iFavDao.getFavById(eventId)
    }

}