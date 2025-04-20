package com.example.bibliotrack.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bibliotrack.data.Book
import com.example.bibliotrack.data.BooksRepository
import java.util.Date

class BookEntryViewModel(private val booksRepository: BooksRepository) : ViewModel() {
    var bookUiState by mutableStateOf(BookUiState())
        private set

    fun updateUiState(bookDetails: BookDetails) {
        bookUiState =
            BookUiState(bookDetails = bookDetails, isEntryValid = validateInput(bookDetails))
    }

    private fun validateInput(uiState: BookDetails = bookUiState.bookDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() && author.isNotBlank() && chapters.isNotBlank() && pages.isNotBlank() && rating.isNotBlank()
        }
    }

    suspend fun saveBook() {
        if (validateInput()) {
            booksRepository.insertBook((bookUiState.bookDetails.toBook()))
        }
    }

}

data class BookUiState(
    val bookDetails: BookDetails = BookDetails(),
    val isEntryValid: Boolean = false
)

data class BookDetails(
    val id: Int = 0,
    val title: String = "",
    val author: String = "",
    val chapters: String = "",
    val chaptersRead: String = "",
    val pages: String = "",
    val pagesRead: String = "",
    val finished: String = "",
    val rating: String = "",
    val createdAt: Date = Date()
)

fun BookDetails.toBook(): Book = Book(
    id = id,
    title = title,
    author = author,
    chapters = chapters.toIntOrNull() ?: 0,
    chaptersRead = chaptersRead.toIntOrNull() ?: 0,
    pages = pages.toIntOrNull() ?: 0,
    pagesRead = pagesRead.toIntOrNull() ?: 0,
    finished = finished.toBoolean(),
    rating = rating.toDoubleOrNull() ?: 0.0,
    createdAt = createdAt
)

fun Book.toBookUiState(isEntryValid: Boolean = false): BookUiState = BookUiState(
    bookDetails = this.toBookDetails(),
    isEntryValid = isEntryValid
)

fun Book.toBookDetails(): BookDetails = BookDetails(
    id = id,
    title = title,
    author = author,
    chapters = chapters.toString(),
    chaptersRead = chaptersRead.toString(),
    pages = pages.toString(),
    pagesRead = pagesRead.toString(),
    finished = finished.toString(),
    rating = rating.toString(),
    createdAt = createdAt
)