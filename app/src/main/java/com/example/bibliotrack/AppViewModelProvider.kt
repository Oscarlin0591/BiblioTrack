package com.example.bibliotrack

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bibliotrack.model.BookDetailsViewModel
import com.example.bibliotrack.model.BookEditViewModel
import com.example.bibliotrack.model.BookEntryViewModel
import com.example.bibliotrack.model.ListViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEntryViewModel
        initializer {
            BookEntryViewModel(inventoryApplication().container.booksRepository)
        }

        // Initializer for BookEditViewModel
        initializer {
            BookEditViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.booksRepository
            )
        }

        initializer {
            BookDetailsViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.booksRepository
            )
        }

    }
}

fun CreationExtras.inventoryApplication(): BiblioTrackApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as BiblioTrackApplication)
