package com.example.bibliotrack.screens

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bibliotrack.MyApp
import com.example.bibliotrack.model.BookViewModel
import com.example.bibliotrack.navigation.AppBar
import com.example.bibliotrack.navigation.AppScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun AddBookScreen(
    navController: NavController,
    bookViewModel: BookViewModel
){
    Scaffold(
        topBar = {
            AppBar(
                currentScreen = AppScreens.AboutScreen.name,
                navController = navController,
                navigateUp = { navController.navigateUp() },
                textToShare = "",
                context = LocalContext.current,
                bookViewModel = bookViewModel,
                modifier = Modifier
            )
        },
        //containerColor = bookViewModel.backgroundColor

    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
            Text("Title:")
            TextField("...", onValueChange = {})

            Text("Author:")
            TextField("...", onValueChange = {})

            Text("Total chapters:")
            TextField("...", onValueChange = {})

            Text("Chapters read:")
            TextField("...", onValueChange = {})

            Text("Total pages:")
            TextField("...", onValueChange = {})

            Text("Pages read:")
            TextField("...", onValueChange = {})

            Button(onClick = {}, modifier = Modifier.align(Alignment.End).padding(20.dp)) {
                Text("Save")
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApp {
        //AddBookScreen()
    }
}