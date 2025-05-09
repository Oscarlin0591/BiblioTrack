package com.example.bibliotrack.data

/*
BiblioTrack
Author: Jacob Levin & Oscar Lin
04/12/2025
This is the Book data class that defines the
parameters that the Book class contains
 */

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "books")
data class Book (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val author: String,
    val chapters: Int,
    val chaptersRead: Int,
    val pages: Int,
    val pagesRead: Int,
    val finished: Boolean,
    val rating: Double,
    val createdAt: String
)
