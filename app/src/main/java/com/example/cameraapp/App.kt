package com.example.cameraapp

import android.app.Application
import com.example.cameraapp.model.db.AppDatabase

class App : Application() {
    companion object {
        lateinit var instance: AppDatabase
    }

    override fun onCreate() {
        instance = AppDatabase.invoke(this)
        super.onCreate()
    }
}