package com.example.bibliotrack.screens

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
        //containerColor = MaterialTheme.colorScheme.primaryContainer,
        topBar = {
            AppBar(
                currentScreen = AppScreens.HomeScreen.name,
                navController = navController,
                navigateUp = { navController.navigateUp() },
                context = LocalContext.current,
                textToShare = "",
                bookEntryViewModel = bookEntryViewModel,
                modifier = Modifier
            )
        },
        bottomBar = {
            BottomAppBar (
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.Black,
                modifier = Modifier.windowInsetsBottomHeight(insets = WindowInsets(bottom=50.dp))
            ) {}
        },
        containerColor = bookEntryViewModel.backgroundColor
    ) {

        Column (modifier = Modifier
            .fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Text("Welcome to BiblioTrack!", modifier = Modifier.padding(bottom = 24.dp), color = Color.Black)
            Button(onClick ={
                navController.navigate(route = AppScreens.BookListScreen.name)}
            ) {
                Text("Tap here to start!")
            }
        }
    }
}