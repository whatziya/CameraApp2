package com.example.cameraapp.model.repository

import androidx.lifecycle.LiveData
import com.example.cameraapp.model.db.AppDao
import com.example.cameraapp.model.entity.CamData

class GalleryRepository(
    private val galleryDao: AppDao
) {

    fun add(model:CamData){
        galleryDao.add(model)
    }

    fun delete(id:Int) {
        galleryDao.delete(id)
    }

    fun getAll(): LiveData<List<CamData>> {
        return galleryDao.getAll()
    }

    suspend fun getAllDataList() = galleryDao.getAllDataList()
}