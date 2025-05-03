package com.example.bibliotrack.screens

/*
BiblioTrack
Author: Jacob Levin & Oscar Lin
04/12/2025
UI Composable to add a book to your app
 */

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
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
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            PlainBar(
                currentScreen = AppScreens.AboutScreen.name,
                navController = navController,
                navigateUp = { navController.navigateUp() },
                context = LocalContext.current,
                modifier = Modifier
            )
        },
        containerColor = bookEntryViewModel.backgroundColor

    ) {
        BookEntryBody(
            bookUiState = bookEntryViewModel.bookUiState,
            onBookValueChange = bookEntryViewModel::updateUiState,
            onSaveClick = { // calls function from view model to save book, reset textfields, and navigates back.
                coroutineScope.launch {
                    bookEntryViewModel.saveBook()
                    bookEntryViewModel.reset()
                    navController.navigateUp()
                }
            }
        )
    }
}

// Form composable containing textfields
@Composable
fun BookForm(
    bookDetails: BookDetails,
    onValueChange: (BookDetails) -> Unit = {},
    modifier: Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 100.dp)
    ) {
        Text("Title:")
        OutlinedTextField(
            value = bookDetails.title,
            onValueChange = { onValueChange(bookDetails.copy(title = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )

        Text("Author:")
        OutlinedTextField(
            value = bookDetails.author,
            onValueChange = { onValueChange(bookDetails.copy(author = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )

        Text("Total chapters:")
        OutlinedTextField(
            value = bookDetails.chapters,
            onValueChange = { onValueChange(bookDetails.copy(chapters = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )

        Text("Chapters read:")
        OutlinedTextField(
            value = bookDetails.chaptersRead,
            onValueChange = { onValueChange(bookDetails.copy(chaptersRead = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )

        Text("Total pages:")
        OutlinedTextField(
            value = bookDetails.pages,
            onValueChange = { onValueChange(bookDetails.copy(pages = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )

        Text("Pages read:")
        OutlinedTextField(
            value = bookDetails.pagesRead,
            onValueChange = { onValueChange(bookDetails.copy(pagesRead = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )

        Text("Rating:")
        OutlinedTextField(
            value = bookDetails.rating,
            onValueChange = { onValueChange(bookDetails.copy(rating = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        )

    }
}

@Composable
fun BookEntryBody(
    bookUiState: BookUiState,
    onBookValueChange: (BookDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        BookForm(
            bookDetails = bookUiState.bookDetails,
            onValueChange = onBookValueChange,
            modifier = modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = bookUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = modifier.fillMaxWidth(.7f).padding(top = 16.dp)
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
                title = "something",
                author = "man",
                chapters = "4",
                chaptersRead = "3",
                pagesRead = "4",
                pages = "9",
                id = 1,
                finished = "true",
                rating = "1",
                createdAt = "12-21-2024"
            )
        ), onBookValueChange = {}, onSaveClick = {})
    }
}