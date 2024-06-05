package com.example.cameraapp.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cameraapp.model.entity.CamData

@Dao
interface AppDao {
    @Query("SELECT * FROM data")
    fun getAll(): LiveData<List<CamData>>

    @Query("SELECT * FROM data")
    suspend fun getAllDataList(): List<CamData>

    @Insert
    fun add(entity: CamData)

    @Query("DELETE FROM data WHERE  :id")
    fun delete(id: Int)

}