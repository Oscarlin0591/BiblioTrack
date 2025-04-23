package com.example.bibliotrack.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bibliotrack.data.Book
import com.example.bibliotrack.navigation.AppBar
import com.example.bibliotrack.navigation.AppScreens
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    navController: NavController,
//    bookViewModel: BookViewModel
) {
    val bookList = listOf(
        Book(id = 1, title = "Title 1", author = "Author 1", finished = false, chapters = 10, chaptersRead = 1, pages = 100, pagesRead = 20, rating = 5.0, createdAt = Date()),
        Book(id=2, title="Title 2", author ="Author 1", finished = false, chapters = 10, chaptersRead = 1, pages = 100, pagesRead = 20, rating = 5.0, createdAt = Date())
    )
    Scaffold(
        topBar = { //top app bar
            AppBar(
                currentScreen = AppScreens.HomeScreen.name,
                navController = navController,
                navigateUp = { navController.navigateUp() },
                context = LocalContext.current,
                textToShare = "",
//                bookViewModel = bookViewModel,
                modifier = Modifier
            )
        },
        bottomBar = { //bottom app bar for cleaner look of the app
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.Black,
                modifier = Modifier.windowInsetsBottomHeight(insets = WindowInsets(bottom=50.dp))
            ) {}
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                onClick = {
                    navController.navigate(route= AppScreens.AddBookScreen.name)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Book"
                )
            }
        }
//        containerColor = bookViewModel.backgroundColor
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 36.dp), verticalArrangement = Arrangement.Center) {
            LazyVerticalGrid(
                //lazy column for all the games
                columns = GridCells.Fixed(1),
            ) {
               items(bookList){
                   BookCard(book = it) {book ->
                       navController.navigate(route = AppScreens.DetailScreen.name + "/$book")
                   }
               }
            }
        }
    }
}

@Composable
fun BookCard(
    book: Book,
    itemClick: (String) -> Unit = {}
) {
    //This composable contains the cards for each game

    Card(
        colors = CardColors(
            MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.onErrorContainer,
            disabledContentColor = MaterialTheme.colorScheme.onError
        ),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(.5f)
            .height(250.dp)
            .clickable {
//                add itemclick
                itemClick(book.title)
            },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text( //game title
                text = "Title: ${book.title}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text( // game genre
                text = "Author: ${book.author}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 10.dp)
            )

        }
    }
}

