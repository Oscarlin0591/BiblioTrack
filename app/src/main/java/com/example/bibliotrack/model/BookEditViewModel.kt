//package com.example.bibliotrack.model
//
//import android.util.Log
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.bibliotrack.data.BooksRepository
//import kotlinx.coroutines.flow.filterNotNull
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.launch
//
//class BookEditViewModel(
//    savedStateHandle: SavedStateHandle,
//    private val booksRepository: BooksRepository
//) : ViewModel() {
//
//    private var bookId : Int = 0
//
//    init {
//        Log.d("BookEditViewModel", "Available keys: ${savedStateHandle.keys()}")
//        Log.d("BookEditViewModel", "bookId: ${savedStateHandle.get<Int>("bookId")}")
//
//        viewModelScope.launch {
//            val bookFlow = booksRepository.getAllBooksStream()
//            val bookList = bookFlow.filterNotNull().first()
//            bookUiState = if (bookList.find{it.id == bookId} != null) {
//                bookList.find { it.id == bookId }!!
//                    .toBookUiState(true)
//            } else {
//                bookList.first().toBookUiState(true)
//            }
//
//            Log.d("bookUiState", bookUiState.bookDetails.id.toString())
//        }
//    }
//
//    var bookUiState by mutableStateOf(BookUiState())
//        private set
//
//    private fun validateInput(uiState: BookDetails = bookUiState.bookDetails): Boolean {
//        return with(uiState) {
//            title.isNotBlank() && author.isNotBlank() && chapters.isNotBlank() && chaptersRead.isNotBlank() && pages.isNotBlank() && pagesRead.isNotBlank()
//        }
//    }
//
//    fun updateUiState(bookDetails: BookDetails) {
//        bookUiState =
//            BookUiState(bookDetails = bookDetails, isEntryValid = validateInput(bookDetails))
//    }
//
//    suspend fun updateItem() {
//        if (validateInput(bookUiState.bookDetails)) {
//            booksRepository.updateBook(bookUiState.bookDetails.toBook())
//        }
//    }
//
//    fun setId(id: Int) {
//        bookId = id
//        Log.d("Id Update", id.toString())
//
//    }
//
//}