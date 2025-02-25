package com.example.marchapplication.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val NameProduct: String,
    val FilePath: String,
    val Image :Int,
    val CarName: String,
    val CapturedBy: String,
    val DateCaptured: String,
    val Location: String,
    val TextEN: String,
    val TextJN: String?,
)


