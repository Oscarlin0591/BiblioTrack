package com.example.bibliotrack.navigation

enum class AppScreens {
    HomeScreen,
    DetailScreen,
    AboutScreen,
    SettingScreen;
    companion object {
        fun fromRoute (route: String?): AppScreens
            = when(route?.substringBefore("/"))
            {
                HomeScreen.name -> HomeScreen
                DetailScreen.name -> DetailScreen
                AboutScreen.name -> AboutScreen
                SettingScreen.name -> SettingScreen
                null -> HomeScreen
                else -> throw IllegalArgumentException("Route $route is not recognized")
            }

    }
}