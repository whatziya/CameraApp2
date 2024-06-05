package com.example.cameraapp.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class CamData(
    @ColumnInfo(name = "Url")
    val uri: String,
    @ColumnInfo(name = "data")
    val data: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)