package com.example.bibliotrack

import android.app.Application
import com.example.bibliotrack.data.AppContainer
import com.example.bibliotrack.data.AppDataContainer

class BiblioTrackApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}