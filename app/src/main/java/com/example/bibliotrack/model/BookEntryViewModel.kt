package com.example.bibliotrack.model

/*
BiblioTrack
Author: Jacob Levin & Oscar Lin
04/12/2025
Central viewmodel for the app's state change during
runtime.
 */

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliotrack.data.Book
import com.example.bibliotrack.data.BooksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BookEntryViewModel(private val booksRepository: BooksRepository) : ViewModel() {
    var backgroundColor: Color by mutableStateOf(Color.White)
    var dropDownPosition by mutableStateOf(0)
    var query: String by mutableStateOf("")

    // timeout companion obj
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    //the UI state variable that is a Flow of the List of all the books from the database
    var bookListUiState: StateFlow<BookListUiState> by mutableStateOf(
        booksRepository.getAllBooksStream().map { BookListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = BookListUiState()
            )
    )

    // mutable UI state with a single book's information
    var bookUiState by mutableStateOf(BookUiState())
        private set

    //function to update the list to be used in the UI Composables
    fun updateListUiState(query: String) {
        bookListUiState = booksRepository.getQueryStream(query).map { BookListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = BookListUiState()
            )
    }
    // function to update the single book's details in the ui state
    fun updateUiState(bookDetails: BookDetails) {
        bookUiState =
            BookUiState(bookDetails = bookDetails, isEntryValid = validateInput(bookDetails))
    }

    // function to validate if the input details for a book is valid
    private fun validateInput(uiState: BookDetails = bookUiState.bookDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() &&
            author.isNotBlank() &&
            chapters.isNotBlank() &&
            chapters.isDigitsOnly() &&
            pages.isNotBlank() &&
                    (rating.isNotBlank() && rating.toFloat() <= 5.0 && rating.toFloat() >= 0)
        }
    }

    //saves the Book information and inserts the newly created book into the database
    suspend fun saveBook() {
        if (validateInput()) {
            booksRepository.insertBook((bookUiState.bookDetails.toBook()))
        }
    }

    //deletes the given Book object from the database
    suspend fun deleteItem(book: Book) {
        booksRepository.deleteBook(book)
    }

    // updates the given book object with new information
    suspend fun updateItem() {
        if (validateInput(bookUiState.bookDetails)) {
            booksRepository.updateBook(bookUiState.bookDetails.toBook())
        }
    }

    // changes screen color
    fun changeColor(color: Color) {
        this.backgroundColor = color
    }

    // clears book details after adding a book
    fun reset() {
        updateUiState(BookDetails())
    }
}
// the following codes define the data classes of the UIStates and details
// used by the viewModel
data class BookListUiState(val itemList: List<Book> = listOf())

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
    val createdAt: String = ""
)

// extension functions of the data classes
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


