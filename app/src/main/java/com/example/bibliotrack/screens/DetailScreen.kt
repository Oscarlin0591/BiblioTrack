package com.example.bibliotrack.screens

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.rememberNavController
import com.example.bibliotrack.AppViewModelProvider
import com.example.bibliotrack.R
import com.example.bibliotrack.data.Book
import com.example.bibliotrack.model.BookDetailsUiState
import com.example.bibliotrack.model.BookDetailsViewModel
import com.example.bibliotrack.model.BookEntryViewModel
import com.example.bibliotrack.model.toBook
import com.example.bibliotrack.navigation.AppBar
import com.example.bibliotrack.navigation.AppScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun DetailsScreen(
    navController: NavController,
    bookDetailsViewModel: BookDetailsViewModel,
    bookEntryViewModel: BookEntryViewModel,
    bookTitle: String?
) {
    val savedStateRegistryOwner = checkNotNull(LocalSavedStateRegistryOwner.current)
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)

//    val bookDetailsViewModel: BookDetailsViewModel = viewModel(
//        factory = AppViewModelProvider.Factory
//        ,
//        extras = remember {
//            viewModelStoreOwner.defaultViewModelCreationExtras
//                .withSavedState(
//                    savedStateRegistryOwner,
//                    viewModelStoreOwner,
//                    key = "bookTitle",
//                    value = bookTitle
//                )
//        }
//    )
    val uiState = bookDetailsViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            AppBar(
                currentScreen = AppScreens.DetailScreen.name,
                navController = navController,
                navigateUp = { navController.navigateUp() },
                context = LocalContext.current,
                textToShare = "Share Text",
                bookEntryViewModel = bookEntryViewModel,
                modifier = Modifier
            )

        },
        bottomBar = { //bottom app bar for cleaner look of the app
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.secondary,
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
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                ) {
                    append("Book info: ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        shadow = Shadow(Color.Black, Offset(1f, 1f), 0.2f),
                        fontSize = 20.sp
                    )
                ) {
                }

            }, modifier = Modifier.padding(6.dp))
            BookDetailBody(
                bookDetailsUiState = uiState.value,
                modifier = Modifier
            )

        }

    }

}
@Composable
private fun BookDetailBody(
    bookDetailsUiState: BookDetailsUiState,
    modifier: Modifier
) {
    Column {
        BookDetails(
            book = bookDetailsUiState.bookDetails.toBook(),
            modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun BookDetails(
    book: Book, modifier: Modifier = Modifier
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
        }
    }
}