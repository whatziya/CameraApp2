package com.example.cameraapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cameraapp.model.entity.CamData
import com.example.cameraapp.model.repository.GalleryRepository
import kotlinx.coroutines.launch

class GalleryViewModel(
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    private val _list = MutableLiveData<List<CamData>>()
    val list: LiveData<List<CamData>> = _list

    fun getAllDataList() = viewModelScope.launch {
        _list.value = galleryRepository.getAllDataList()
    }

    fun getObs(): LiveData<List<CamData>> {
        return galleryRepository.getAll()
    }

    fun delete(id: Int) {
        galleryRepository.delete(id)
    }

    fun add(entity: CamData) {
        galleryRepository.add(entity)
    }
}