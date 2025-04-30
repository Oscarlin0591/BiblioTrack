package com.example.bibliotrack.data

import androidx.room.Query
import kotlinx.coroutines.flow.Flow

class OfflineBooksRepository(private val bookDao: BookDao) : BooksRepository {
    override fun getAllBooksStream(): Flow<List<Book>> =
        bookDao.getAllBooks()

    override fun getBookStream(id: Int): Flow<Book?> =
        bookDao.getBook(id)

    override fun getQueryStream(query: String): Flow<List<Book>> =
        bookDao.getQueryList(query)

    override fun getAllList(): List<Book> =
        bookDao.getAllList()

    override suspend fun insertBook(book: Book) =
        bookDao.insert(book)

    override suspend fun deleteBook(book: Book) =
        bookDao.delete(book)

    override suspend fun updateBook(book: Book) =
        bookDao.update(book)
}