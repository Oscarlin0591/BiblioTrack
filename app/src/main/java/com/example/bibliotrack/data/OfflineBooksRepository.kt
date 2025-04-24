package com.example.bibliotrack.data

import kotlinx.coroutines.flow.Flow

class OfflineBooksRepository(private val bookDao: BookDao) : BooksRepository {
    override fun getAllBooksStream(): Flow<List<Book>> =
        bookDao.getAllBooks()

    override fun getBookStream(id: Int): Flow<Book?> =
        bookDao.getBook(id)

    override fun getAllList(): List<Book> =
        bookDao.getAllList()

    override suspend fun insertBook(book: Book) =
        bookDao.insert(book)

    override suspend fun deleteBook(book: Book) =
        bookDao.delete(book)

    override suspend fun updateBook(book: Book) =
        bookDao.update(book)
}