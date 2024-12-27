package com.example.dicodingevent.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavEvent::class], version = 1)
abstract class FavRoomDB : RoomDatabase() {
    abstract fun favDao(): FavDao

    companion object {
        @Volatile
        private var INSTANCE: FavRoomDB? = null

        @JvmStatic
        fun getDb(context: Context): FavRoomDB{
            if (INSTANCE == null){
                synchronized(FavRoomDB::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavRoomDB::class.java, "favorite_database").build()
                }
            }
            return INSTANCE as FavRoomDB
        }
    }
}