package com.example.bibliotrack.navigation

/*
BiblioTrack
Author: Jacob Levin & Oscar Lin
04/12/2025
Central navigation class that handles all of the app's navigation
 */

import android.content.Context
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bibliotrack.AppViewModelProvider
import com.example.bibliotrack.model.BookEntryViewModel
import com.example.bibliotrack.screens.AboutScreen
import com.example.bibliotrack.screens.AddBookScreen
import com.example.bibliotrack.screens.BookEditScreen
import com.example.bibliotrack.screens.BookListScreen
import com.example.bibliotrack.screens.ColorChangeScreen
import com.example.bibliotrack.screens.DetailsScreen
import com.example.bibliotrack.screens.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    currentScreen: String,
    navController: NavController,
    navigateUp: () -> Unit,
    textToShare: String,
    textToShare1: String,
    context: Context,
    bookEntryViewModel: BookEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState() // backstack entry
    val canNavigateBack = backStackEntry?.destination?.route != AppScreens.HomeScreen.name // navigate back boolean
    TopAppBar( //custom top app bar
        title = { Text("BiblioTrack", color = MaterialTheme.colorScheme.primaryContainer) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        tint = MaterialTheme.colorScheme.primaryContainer,
                        contentDescription = ""
                    )
                }
            }
        },
        actions = { //action to initialize share function when there is text to share
            if (textToShare.isNotEmpty()) {
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "Check out this book!")
                        putExtra(Intent.EXTRA_TEXT, textToShare)
                        putExtra(Intent.EXTRA_TEXT, textToShare1)
                    }
                    context.startActivity(
                        Intent.createChooser(
                            intent,
                            "Share Option"
                        )
                    )
                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        tint = MaterialTheme.colorScheme.primaryContainer,
                        contentDescription = "Share items"
                    )
                }
            }
            IconButton( // button to navigate to setting screen
                onClick = { navController.navigate(route = AppScreens.ColorChangeScreen.name) }
            ) {
                Icon(Icons.Default.Settings, tint = MaterialTheme.colorScheme.primaryContainer, contentDescription = null)
            }
            IconButton( // button to navigate to about screen
                onClick = { navController.navigate(route = AppScreens.AboutScreen.name) }
            ) {
                Icon(Icons.Default.Info, tint = MaterialTheme.colorScheme.primaryContainer, contentDescription = null)
            }
        }
    )
}

// Plain bar for screens like add book or edit where there is no need for navigation to other screens
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlainBar(
    currentScreen: String,
    navController: NavController,
    navigateUp: () -> Unit,
    context: Context,
    modifier: Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = backStackEntry?.destination?.route != AppScreens.HomeScreen.name
    TopAppBar(
        title = { Text("BiblioTrack", color = MaterialTheme.colorScheme.primaryContainer) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = ""
                    )
                }
            }
        },
    )
}

//navigation composable containing the navhost that handles navigation to screens via routes
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BookNavigation() {
    val navController = rememberNavController()
    val bookEntryViewModel: BookEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)

    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.name,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(AppScreens.HomeScreen.name) {
            HomeScreen(
                navController = navController,
                bookEntryViewModel = bookEntryViewModel
            )
        }

        composable(AppScreens.BookListScreen.name) {
            BookListScreen(
                navController = navController,
                bookEntryViewModel = bookEntryViewModel
            )
        }

        composable(
            route = AppScreens.DetailScreen.name + "/{bookId}", //routes so each book has its unique data passed into the next screen
            arguments = listOf(navArgument(name = "bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("bookId")?.let {
                DetailsScreen(
                    navController = navController,
                    bookEntryViewModel = bookEntryViewModel,
                    it
                )
            }
        }
        composable(AppScreens.AboutScreen.name) {
            AboutScreen(
                navController = navController,
                bookEntryViewModel = bookEntryViewModel
            )
        }
        composable(AppScreens.ColorChangeScreen.name) {
            ColorChangeScreen(
                navController = navController,
                bookEntryViewModel = bookEntryViewModel
            )
        }

        composable(AppScreens.AddBookScreen.name) {
            AddBookScreen(
                navController = navController,
                bookEntryViewModel = bookEntryViewModel
            )
        }

        composable(
            AppScreens.BookEditScreen.name + "/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("bookId")?.let {
                BookEditScreen(
                    navController = navController,
                    viewModel = bookEntryViewModel,
                    it
                )
            }
        }
    }
}