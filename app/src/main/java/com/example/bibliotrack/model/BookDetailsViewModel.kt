package com.example.bibliotrack.model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliotrack.data.Book
import com.example.bibliotrack.data.BooksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BookDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val booksRepository: BooksRepository
) : ViewModel() {

    init {
        Log.d("BookDetailsVM", "SavedStateHandle keys: ${savedStateHandle.keys()}")
        Log.d("BookDetailsVM", "bookTitle: ${savedStateHandle.get<String>("bookTitle")}")
    }

//    private val bookId: Int = checkNotNull(savedStateHandle["id"])
//    val testId: String = checkNotNull(savedStateHandle["title"]) {
//        "bookTitle is missing"
//}

    private val bookId: Int = 1
    val uiState: StateFlow<BookDetailsUiState> =
        booksRepository.getBookStream(bookId)
            .filterNotNull()
            .map {
                BookDetailsUiState(bookDetails = it.toBookDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS ),
                initialValue = BookDetailsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class BookDetailsUiState(
    val bookDetails: BookDetails = BookDetails()
)