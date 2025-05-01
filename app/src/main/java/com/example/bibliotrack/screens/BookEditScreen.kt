package com.example.bibliotrack.screens

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bibliotrack.AppViewModelProvider
import com.example.bibliotrack.model.BookEditViewModel
import com.example.bibliotrack.navigation.AppScreens
import com.example.bibliotrack.navigation.PlainBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookEditScreen(
    navController: NavController,
    viewModel: BookEditViewModel = viewModel()
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
    ) { innerPadding ->
        BookEntryBody(
            bookUiState = viewModel.bookUiState,
            onBookValueChange = viewModel::updateUiState,
            onSaveClick = { },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
        )
    }
}