package com.example.bibliotrack.screens

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bibliotrack.data.Book
import com.example.bibliotrack.model.BookEntryViewModel
import com.example.bibliotrack.model.BookListUiState
import com.example.bibliotrack.navigation.AppBar
import com.example.bibliotrack.navigation.AppScreens
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun DetailsScreen(
    navController: NavController,
    bookEntryViewModel: BookEntryViewModel,
    bookId: Int
) {
    val uiState = bookEntryViewModel.bookListUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val book = uiState.value.itemList.find{it.id == bookId}

    Scaffold(
        topBar = {
            if (book != null) {
                AppBar(
                    currentScreen = AppScreens.DetailScreen.name,
                    navController = navController,
                    navigateUp = { navController.navigateUp() },
                    context = LocalContext.current,
                    textToShare = "Title",
                    textToShare1 = "Check out this book! \n${book.title}: ${book.chaptersRead}/${book.chapters} chapters read",
                    bookEntryViewModel = bookEntryViewModel,
                    modifier = Modifier
                )
            }

        },
        bottomBar = { //bottom app bar for cleaner look of the app
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.Black,
                modifier = Modifier.windowInsetsBottomHeight(insets = WindowInsets(bottom = 50.dp))
            ) {}
        },
        containerColor = bookEntryViewModel.backgroundColor
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(top = 84.dp)
        ) {
            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                ) {
                    append("Book info: ")
                }
            }, modifier = Modifier.padding(6.dp))
            BookDetailBody(
                bookListUiState = uiState.value,
                bookId = bookId,
                onDelete = {
                    coroutineScope.launch {
                        uiState.value.itemList.find { it.id == bookId }
                            ?.let { it1 -> bookEntryViewModel.deleteItem(it1) }
                        navController.navigateUp()
                    }
                },
                modifier = Modifier
            )

        }

    }

}
@Composable
private fun BookDetailBody(
    bookListUiState: BookListUiState,
    bookId: Int,
    onDelete: () -> Unit,
    modifier: Modifier
) {
    Column {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false)  }
        val book = bookListUiState.itemList.find{it.id == bookId}

        if(book != null) {
            BookDetails(
                book = book,
                modifier = Modifier.fillMaxWidth()
            )


        OutlinedButton(
            onClick = { deleteConfirmationRequired = true},
            shape = MaterialTheme.shapes.small,
            colors = ButtonColors(
                MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                disabledContentColor = MaterialTheme.colorScheme.surfaceContainerHigh
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Delete", color = MaterialTheme.colorScheme.primaryContainer)
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(16.dp)
            )
        }
        } else {
            Text("Book not found", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun BookDetails(
    book: Book,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Text(text= book.title)
            Text(text= book.author)
            Text(text = "Chapters: ${book.chaptersRead}/${book.chapters}")
            Text(text = "Pages: ${book.pagesRead}/${book.pages}")
            Text(text = "Ratings: ${book.rating}")

            StarBar(book.rating)

        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text("Attention") },
        text = { Text("Are you sure you want to delete?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("No")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Yes")
            }
        })
}

@Composable
private fun StarBar(
    rating: Double,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        for (i in 1..5) {
            val icon = when {
                i <= rating -> Icons.Filled.Star
                i - rating in 0.5..0.99 -> Icons.AutoMirrored.Filled.StarHalf
                else -> Icons.Filled.StarBorder
            }
            Icon(
                imageVector = icon,
                contentDescription = "Rating",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}