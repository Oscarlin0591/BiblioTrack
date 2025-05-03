package com.example.bibliotrack.data
/*
BiblioTrack
Author: Jacob Levin & Oscar Lin
04/12/2025
Repository interface that defines functions on the database
through the DAO.
 */

import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun getAllBooksStream(): Flow<List<Book>>

    fun getBookStream(id: Int): Flow<Book?>

    fun getAllList(): List<Book>

    fun getQueryStream(query: String): Flow<List<Book>>

    suspend fun insertBook(book: Book)

    suspend fun deleteBook(book: Book)

    suspend fun updateBook(book: Book)

}