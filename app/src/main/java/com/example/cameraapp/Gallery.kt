package com.example.cameraapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cameraapp.databinding.ActivityGalleryBinding
import com.example.cameraapp.model.entity.CamData
import com.example.cameraapp.model.repository.GalleryRepository
import com.example.cameraapp.viewmodel.GalleryViewModel
import com.example.cameraapp.viewmodel.GalleryViewModelProviderFactory

class Gallery : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var galleryViewModel: GalleryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        galleryAdapter = GalleryAdapter()
        onViewCreated()
    }

    private fun onViewCreated(){
        val dao = App.instance.appDao()
        val repo = GalleryRepository(dao)
        val factory = GalleryViewModelProviderFactory(repo)
        galleryViewModel = ViewModelProvider(this, factory)[GalleryViewModel::class.java]
        galleryViewModel.getAllDataList()
        galleryViewModel.list.observe(this) { dataList ->
            galleryAdapter.load(dataList)
        }
        with(binding) {
            rv.adapter = galleryAdapter
        }
    }
}