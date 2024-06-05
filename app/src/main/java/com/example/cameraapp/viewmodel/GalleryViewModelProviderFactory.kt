package com.example.cameraapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.cameraapp.model.repository.GalleryRepository


class GalleryViewModelProviderFactory(
    private val repository: GalleryRepository? = null

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return when {
            modelClass.isAssignableFrom(GalleryViewModel::class.java) ->
                GalleryViewModel(repository!!) as T

            else -> {
                throw RuntimeException()
            }
        }
    }
}