package com.example.bibliotrack.navigation

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bibliotrack.model.BookViewModel
import com.example.bibliotrack.screens.AboutScreen
import com.example.bibliotrack.screens.AddBookScreen
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
    context: Context,
//    bookViewModel: BookViewModel,
    modifier: Modifier
    ) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = backStackEntry?.destination?.route != AppScreens.HomeScreen.name
    TopAppBar(
        title = { Text("BibioTrack") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary
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
        actions = {
            if (textToShare.isNotEmpty()) {
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "Check out this book!")
                        putExtra(Intent.EXTRA_TEXT, textToShare)
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
                        contentDescription = "Share items"
                    )
                }
            }
            IconButton(
                onClick = {navController.navigate(route = AppScreens.ColorChangeScreen.name)}
            ) {
                Icon(Icons.Default.Settings, contentDescription = null)
            }
            IconButton(
                onClick = {navController.navigate(route = AppScreens.AboutScreen.name)}
            ) {
                Icon(Icons.Default.Info, contentDescription = null)
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BookNavigation() {
    val navController = rememberNavController()
//    val bookViewModel: BookViewModel = viewModel()
//    bookViewModel.getData()

    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.name,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(AppScreens.HomeScreen.name) {
            HomeScreen(
                navController = navController,
//                bookViewModel
            )
        }

        composable(AppScreens.BookListScreen.name) {
            BookListScreen(
                navController = navController
            )
        }

        composable(AppScreens.DetailScreen.name + "/{title}",
//            arguments = listOf(navArgument(name = "title") { type = NavType.StringType})
        ) { backStackEntry ->
            DetailsScreen(
                navController = navController,
        //                bookViewModel,
                backStackEntry.arguments?.getString("title"),
            )
        }
        composable(AppScreens.AboutScreen.name) {
            AboutScreen(
                navController = navController,
//                bookViewModel
            )
        }
        composable(AppScreens.ColorChangeScreen.name) {
            ColorChangeScreen(
                navController = navController,
//                bookViewModel
            )
        }

        composable(AppScreens.AddBookScreen.name) {
            AddBookScreen(
                navController = navController
            )
        }
    }
}