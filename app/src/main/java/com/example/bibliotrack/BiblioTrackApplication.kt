package com.example.bibliotrack

/*
BiblioTrack
Author: Jacob Levin & Oscar Lin
04/12/2025
Application container to persist the database and other data throughout the app
 */

import android.app.Application
import com.example.bibliotrack.data.AppContainer
import com.example.bibliotrack.data.AppDataContainer

class BiblioTrackApplication: Application() { // initializes the application
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}