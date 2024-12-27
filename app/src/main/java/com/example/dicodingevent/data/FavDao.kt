package com.example.dicodingevent.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert (fav: FavEvent)

    @Delete
    fun delete (fav: FavEvent)

    @Query("SELECT * FROM FavEvent ORDER BY id ASC")
    fun getAllFav(): LiveData<List<FavEvent>>

    @Query("SELECT * FROM FavEvent WHERE id = :eventId LIMIT 1")
    fun getFavById(eventId: Int): LiveData<FavEvent?>
}
