package com.example.bibliotrack.navigation

import com.example.bibliotrack.screens.AddBookScreen

enum class AppScreens {
    HomeScreen,
    DetailScreen,
    AboutScreen,
    SettingScreen,
    AddBookScreen,
    ColorChangeScreen,
    BookListScreen;
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
                null -> HomeScreen
                else -> throw IllegalArgumentException("Route $route is not recognized")
            }

    }
}