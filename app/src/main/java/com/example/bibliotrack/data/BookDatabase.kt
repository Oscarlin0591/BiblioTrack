package com.example.bibliotrack.data
/*
BiblioTrack
Author: Jacob Levin & Oscar Lin
04/12/2025
Database class through Android's Room library
 */
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var Instance: BookDatabase? = null

        //Database getter
        fun getDatabase(context: Context): BookDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context = context, klass = BookDatabase::class.java, name = "book_database")
                    .build()
                    .also{ Instance=it}
            }
        }
    }
}