package com.example.bibliotrack

/*
BiblioTrack
Author: Jacob Levin & Oscar Lin
04/12/2025
ViewModelProvider class to initialize viewmodels for the app
 */

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bibliotrack.model.BookEntryViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider { // creates viewModels through the viewModelFactory
    val Factory = viewModelFactory {
        // Initializer for ItemEntryViewModel
        initializer {
            BookEntryViewModel(inventoryApplication().container.booksRepository)
        }
    }
}

fun CreationExtras.inventoryApplication(): BiblioTrackApplication = //casts application key as the BiblioTrackApplication
    (this[AndroidViewModelFactory.APPLICATION_KEY] as BiblioTrackApplication)
