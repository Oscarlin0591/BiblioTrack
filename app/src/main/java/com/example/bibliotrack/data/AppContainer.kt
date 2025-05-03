package com.example.bibliotrack.data
/*
BiblioTrack
Author: Jacob Levin & Oscar Lin
04/12/2025
AppContainer class is used to contain the repository to be accessible throughout the app
 */
import android.content.Context

//AppContainer interface
interface AppContainer {
    val booksRepository: BooksRepository
}

//Appcontainer class to initialize the repository
class AppDataContainer(private val context: Context) : AppContainer {

    override val booksRepository: BooksRepository by lazy {
        OfflineBooksRepository(BookDatabase.getDatabase(context).bookDao())
    }
}