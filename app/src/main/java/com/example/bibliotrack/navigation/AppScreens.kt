package com.example.bibliotrack.navigation

import com.example.bibliotrack.screens.AddBookScreen
import com.example.bibliotrack.screens.BookEditScreen

enum class AppScreens {
    HomeScreen,
    DetailScreen,
    AboutScreen,
    SettingScreen,
    AddBookScreen,
    ColorChangeScreen,
    BookListScreen,
    BookEditScreen;
    companion object {
        fun fromRoute (route: String?): AppScreens
            = when(route?.substringBefore("/"))
            {
                HomeScreen.name -> HomeScreen
                DetailScreen.name -> DetailScreen
                AboutScreen.name -> AboutScreen
                SettingScreen.name -> SettingScreen
                AddBookScreen.name -> AddBookScreen
                ColorChangeScreen.name -> ColorChangeScreen
                BookListScreen.name -> BookListScreen
                BookEditScreen.name -> BookEditScreen
                null -> HomeScreen
                else -> throw IllegalArgumentException("Route $route is not recognized")
            }

    }
}