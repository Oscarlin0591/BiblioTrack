package com.example.bibliotrack.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class BookDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
//    private val bookId: Int = checkNotNull(savedStateHandle[BookDetailsDestination.bookIdArg])

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class BookDetailsUiState(
    val bookDetails: BookDetails = BookDetails()
)