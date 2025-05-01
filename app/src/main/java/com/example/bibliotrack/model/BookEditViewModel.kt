package com.example.bibliotrack.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bibliotrack.data.BooksRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BookEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val booksRepository: BooksRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            println(savedStateHandle)
            val bookFlow = booksRepository.getAllBooksStream()
            val bookList = bookFlow.filterNotNull().first()
//            bookUiState = bookList.get(0)
//                .toBookUiState(true)
        }
    }

    var bookUiState by mutableStateOf(BookUiState())
        private set
//    private val bookId: Int = 1
//        checkNotNull(savedStateHandle[])

    private fun validateInput(uiState: BookDetails = bookUiState.bookDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() && author.isNotBlank() && chapters.isNotBlank() && chaptersRead.isNotBlank() && pages.isNotBlank() && pagesRead.isNotBlank()
        }
    }

    fun updateUiState(bookDetails: BookDetails) {
        bookUiState =
            BookUiState(bookDetails = bookDetails, isEntryValid = validateInput(bookDetails))
    }

}