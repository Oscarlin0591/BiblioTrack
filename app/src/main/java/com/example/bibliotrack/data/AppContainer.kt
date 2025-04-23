package com.example.bibliotrack.data

import android.content.Context

interface AppContainer {
    val booksRepository: BooksRepository
}
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val booksRepository: BooksRepository by lazy {
        OfflineBooksRepository(BookDatabase.getDatabase(context).bookDao())
    }
}