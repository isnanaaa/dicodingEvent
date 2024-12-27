package com.example.dicodingevent.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavEvent(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var name: String = "",
    var mediaCover: String? = null
) : Parcelable
