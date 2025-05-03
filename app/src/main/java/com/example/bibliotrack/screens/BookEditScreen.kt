package com.example.bibliotrack.screens
/*
BiblioTrack
Author: Jacob Levin & Oscar Lin
04/12/2025
BookEditScreen to update a given book's details by the user
 */

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.NavController
import com.example.bibliotrack.model.BookEntryViewModel
import com.example.bibliotrack.model.toBookDetails
import com.example.bibliotrack.navigation.AppScreens
import com.example.bibliotrack.navigation.PlainBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookEditScreen(
    navController: NavController,
    viewModel: BookEntryViewModel,
    bookId: Int
) {
    val uiState = viewModel.bookListUiState.collectAsState()
    if (uiState.value.itemList.find { it.id == bookId } != null) { //searches for the book to find the exact book the user clicked
        val currentBookDetails = uiState.value.itemList.find { it.id == bookId }!!.toBookDetails()
        viewModel.updateUiState(currentBookDetails)
    }
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
    ) { innerPadding ->
        BookEntryBody( // same uiState with the given book's information
            bookUiState = viewModel.bookUiState,
            onBookValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateItem()
                    viewModel.reset()
                    navController.navigateUp()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
                )
                .verticalScroll(rememberScrollState())
        )
    }
}