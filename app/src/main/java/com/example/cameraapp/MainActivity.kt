package com.example.cameraapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.cameraapp.databinding.ActivityMainBinding
import com.example.cameraapp.model.entity.CamData
import com.example.cameraapp.model.repository.GalleryRepository
import com.example.cameraapp.viewmodel.GalleryViewModel
import com.example.cameraapp.viewmodel.GalleryViewModelProviderFactory
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var galleryViewModel: GalleryViewModel

    //gallery = 1
    @SuppressLint("SimpleDateFormat")
    private val launcher1 =
        registerForActivityResult(ActivityResultContracts.GetContent()) {

            it?.let {
                val file = contentResolver.openInputStream(it)
                val string =
                    SimpleDateFormat("${System.currentTimeMillis()}").format(Date(System.currentTimeMillis()))
                val saveFile = File(cacheDir, "$string.jpg")
                // saveFile.createNewFile()

                val outputStream = saveFile.outputStream()
                file?.copyTo(outputStream)
                file?.close()
                outputStream.close()

                galleryViewModel.add(CamData(saveFile.absolutePath, string))

            }

        }


    @SuppressLint("SimpleDateFormat")
    private val launcher2 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val string =
                    SimpleDateFormat("dd/mm/yyyy hh:mm").format(Date(System.currentTimeMillis()))
                galleryViewModel.add(CamData(currentPhotoPath, string, 0))

            }

        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onViewCreated()
        onClicks()


    }
    private fun onViewCreated(){
        val dao = App.instance.appDao()
        val repo = GalleryRepository(dao)
        val factory = GalleryViewModelProviderFactory(repo)
        galleryViewModel = ViewModelProvider(this, factory)[GalleryViewModel::class.java]
        val list1 = mutableListOf<CamData>()
//        galleryViewModel.getAll().forEach {
//            list1.add(it)
//        }
//        binding.galleryFab.setImageURI(Uri.parse(list1.last().uri))
    }

    private fun onClicks() {
        with(binding) {
            galleryFab.setOnClickListener {
                val intent = Intent(this@MainActivity, Gallery::class.java)
                startActivity(intent)
            }
            cameraFab.setOnClickListener {
                dispatchTakePictureIntent()
            }
            photoFab.setOnClickListener {
                launcher1.launch("image/*")

            }
        }
    }
    private lateinit var currentPhotoPath: String
    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File =
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "com.example.android.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                launcher2.launch(takePictureIntent)
            }

        }
    }

}