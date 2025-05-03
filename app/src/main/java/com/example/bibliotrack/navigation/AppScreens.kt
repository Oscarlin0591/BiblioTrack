package com.example.bibliotrack.navigation

/*
BiblioTrack
Author: Jacob Levin & Oscar Lin
04/12/2025
Screen enum class that defines values for navigation
 */

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