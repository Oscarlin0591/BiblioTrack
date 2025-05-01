package com.example.bibliotrack.screens

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bibliotrack.MyApp
import com.example.bibliotrack.model.BookDetails
import com.example.bibliotrack.model.BookEntryViewModel
import com.example.bibliotrack.model.BookUiState
import com.example.bibliotrack.navigation.AppScreens
import com.example.bibliotrack.navigation.PlainBar
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun AddBookScreen(
    navController: NavController,
    bookEntryViewModel: BookEntryViewModel = viewModel()
){
    val coroutineScope = rememberCoroutineScope()
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
        containerColor = bookEntryViewModel.backgroundColor

    ) {
        BookEntryBody(
            bookUiState = bookEntryViewModel.bookUiState,
            onBookValueChange = bookEntryViewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    bookEntryViewModel.saveBook()
                    navController.navigateUp()
                }
            }
        )
    }
}

@Composable
fun BookForm(
    bookDetails: BookDetails,
    onValueChange: (BookDetails) -> Unit = {},
    modifier: Modifier
){

    Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(top = 100.dp)){
        Text("Title:")
        TextField(
            value = bookDetails.title,
            onValueChange = {onValueChange(bookDetails.copy(title = it))},
        )

        Text("Author:")
        TextField(
            value = bookDetails.author,
            onValueChange = {onValueChange(bookDetails.copy(author = it))},
        )

        Text("Total chapters:")
        TextField(
            value = bookDetails.chapters,
            onValueChange = {onValueChange(bookDetails.copy(chapters = it))},
        )

        Text("Chapters read:")
        TextField(
            value = bookDetails.chaptersRead,
            onValueChange = {onValueChange(bookDetails.copy(chaptersRead = it))},
        )

        Text("Total pages:")
        TextField(
            value = bookDetails.pages,
            onValueChange = {onValueChange(bookDetails.copy(pages = it))},
        )

        Text("Pages read:")
        TextField(
            value = bookDetails.pagesRead,
            onValueChange = {onValueChange(bookDetails.copy(pagesRead = it))},
        )

        Text("Rating:")
        TextField(
            value = bookDetails.rating,
            onValueChange = {onValueChange(bookDetails.copy(rating = it))},
        )

    }
}

@Composable
fun BookEntryBody(
    bookUiState: BookUiState,
    onBookValueChange: (BookDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column() {
        BookForm(
            bookDetails = bookUiState.bookDetails,
            onValueChange = onBookValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = bookUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
        ) {
            Text("Save")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApp {
        BookEntryBody(bookUiState = BookUiState(
            BookDetails(
                title = "something", author = "man", chapters = "4", chaptersRead = "3", pagesRead = "4", pages = "9", id = 1, finished = "true", rating = "1", createdAt = "12-21-2024"
            )
        ), onBookValueChange = {}, onSaveClick = {})
    }
}