package com.example.bibliotrack.screens

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bibliotrack.model.BookEntryViewModel
import com.example.bibliotrack.navigation.AppScreens
import com.example.bibliotrack.navigation.PlainBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun AboutScreen(
    navController: NavController,
    bookEntryViewModel: BookEntryViewModel
) {
    Scaffold(
        topBar = {
            PlainBar(
                currentScreen = AppScreens.AboutScreen.name,
                navController = navController,
                navigateUp = { navController.navigateUp() },
                textToShare = "",
                context = LocalContext.current,
                modifier = Modifier
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.Black,
                modifier = Modifier.windowInsetsBottomHeight(insets = WindowInsets(bottom = 50.dp))
            ) {}
        },
        containerColor = bookEntryViewModel.backgroundColor
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Sometimes you drop a book and some time later you want to pick it back up again, " +
                        "\nbut you forgot where you left off! That’s where our app comes in! " +
                        "\nIt’s an all-in-one bookmarking app. The app allows you to manually input where you left off on a book, so you don’t have to guess what chapter you last viewed. " +
                        "\nThis app is perfect for people who read a lot of books. ",
                modifier = Modifier.padding(8.dp)
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 52.dp))
            Text(
                text = "This app is made by Jacob Levin and Oscar Lin",
                style = MaterialTheme.typography.titleLarge
            )
        }

    }
}