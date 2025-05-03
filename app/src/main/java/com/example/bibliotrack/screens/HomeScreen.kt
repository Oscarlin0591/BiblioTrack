package com.example.bibliotrack.screens

/*
BiblioTrack
Author: Jacob Levin & Oscar Lin
04/12/2025
HomeScreen is the landing screen the user starts at.
 */

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.bibliotrack.navigation.AppBar
import com.example.bibliotrack.navigation.AppScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    bookEntryViewModel: BookEntryViewModel
) {
    Scaffold(
        containerColor = bookEntryViewModel.backgroundColor,
        topBar = {
            AppBar(
                currentScreen = AppScreens.HomeScreen.name,
                navController = navController,
                navigateUp = { navController.navigateUp() },
                context = LocalContext.current,
                textToShare = "",
                textToShare1 = "",
                bookEntryViewModel = bookEntryViewModel,
                modifier = Modifier
            )
        },
        bottomBar = {
            BottomAppBar (
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.Black,
                modifier = Modifier.windowInsetsBottomHeight(insets = WindowInsets(bottom=50.dp))
            ) {}
        }
    ) {

        Column (modifier = Modifier
            .fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Text("Welcome to BiblioTrack!", modifier = Modifier.padding(bottom = 24.dp), color = Color.Black)//MaterialTheme.colorScheme.onPrimary)
            Button(onClick ={
                navController.navigate(route = AppScreens.BookListScreen.name)}
            ) {
                Text("Start")
            }
        }
    }
}