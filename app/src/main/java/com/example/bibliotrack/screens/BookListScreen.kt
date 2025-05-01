package com.example.bibliotrack.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bibliotrack.data.Book
import com.example.bibliotrack.model.BookEntryViewModel
import com.example.bibliotrack.navigation.AppBar
import com.example.bibliotrack.navigation.AppScreens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    navController: NavController,
    bookEntryViewModel: BookEntryViewModel
) {
    val bookListUiState by bookEntryViewModel.bookListUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = { //top app bar
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
        bottomBar = { //bottom app bar for cleaner look of the app
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.Black,
                modifier = Modifier.windowInsetsBottomHeight(insets = WindowInsets(bottom = 50.dp))
            ) {}
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                onClick = {
                    navController.navigate(route = AppScreens.AddBookScreen.name)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Book"
                )
            }
        },
        containerColor = bookEntryViewModel.backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 90.dp), verticalArrangement = Arrangement.Top
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                TextField(
                    onValueChange = {
                        bookEntryViewModel.query = it
                        coroutineScope.launch {
                            bookEntryViewModel.updateListUiState(bookEntryViewModel.query)
                        }
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    placeholder = {
                        Text("Search for a book")
                    },
                    value = bookEntryViewModel.query
                )
            }

            if (bookListUiState.itemList.isNotEmpty()) {
                LazyVerticalGrid(
                    //lazy column for all the books
                    columns = GridCells.Fixed(1)
                ) {
                    items(items = bookListUiState.itemList, key = { it.id }) { book ->
                        BookCard(
                            book = book,
                            navController = navController,
                            bookEntryViewModel = bookEntryViewModel,
                        ) {
                            navController.navigate(route = AppScreens.DetailScreen.name + "/${book.id}")
                        }
                    }
                }
            } else {
                Text("Tab the + button to add a bookmark")
            }

        }
    }


}

@Composable
fun BookCard(
    book: Book,
    navController: NavController,
    bookEntryViewModel: BookEntryViewModel,
    itemClick: (String) -> Unit = {}
) {
    //This composable contains the cards for each book

    Card(
        colors = CardColors(
            MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(.5f)
            .height(100.dp)
            .clickable {
                itemClick(book.title)
            },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier.padding(5.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text( //book title
                    text = "Title: ${book.title}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Text( // book genre
                    text = "Author: ${book.author}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 10.dp)
                )

            }
            Button(onClick = {
                navController.navigate(route = AppScreens.BookEditScreen.name + "/${book.id}")
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            }
        }

    }
}

