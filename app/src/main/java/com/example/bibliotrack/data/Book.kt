package com.example.bibliotrack.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Book (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    val finished: Boolean
)
