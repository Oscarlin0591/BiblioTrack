package com.example.bibliotrack.screens

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bibliotrack.MyApp
import com.example.bibliotrack.model.BookEntryViewModel
import com.example.bibliotrack.navigation.AppScreens
import com.example.bibliotrack.navigation.PlainBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun ColorChangeScreen(
    navController: NavController,
    bookEntryViewModel: BookEntryViewModel
) {
    val isExpanded = remember { mutableStateOf(false) }
    val colors = listOf("Light","Dark", "Default", "Yellow", "Purple", "Green")
    val defaultColor = MaterialTheme.colorScheme.secondary
    @Composable
    fun DropDown() {
        Column {
            Row (modifier = Modifier.clickable { isExpanded.value = true }){
                Text(colors[bookEntryViewModel.dropDownPosition], color = Color.Black)//MaterialTheme.colorScheme.onPrimary)
                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
            }
        }
    }


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
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.Black,
                modifier = Modifier.windowInsetsBottomHeight(insets = WindowInsets(bottom = 50.dp))
            ) {}
        },
        containerColor = bookEntryViewModel.backgroundColor

    ) {

        Column( // column of buttons that change the background color of each screen
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxWidth()
        ) {
            Text(
                "Change the background color by tapping a color:",
                color = Color.Black,//MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )
            Box {
                DropDown()
                DropdownMenu(
                    expanded = isExpanded.value,
                    onDismissRequest = { isExpanded.value = false }) {
                    colors.forEachIndexed { index, color ->
                        DropdownMenuItem(
                            text = {
                                Text(text = color, color = MaterialTheme.colorScheme.onPrimary)
                            },
                            onClick = {
                                isExpanded.value = false

                                when(color){
                                    "Default" -> {
                                        bookEntryViewModel.changeColor(defaultColor)
                                    }
                                    "Light" -> {
                                        bookEntryViewModel.changeColor(Color.White)
                                    }
                                    "Dark" -> {
                                        bookEntryViewModel.changeColor(Color.Black)
                                    }
                                    "Yellow" -> {
                                        bookEntryViewModel.changeColor(Color.Yellow)
                                    }
                                    "Purple" -> {
                                        bookEntryViewModel.changeColor(Color.Magenta)
                                    }
                                    "Green" -> {
                                        bookEntryViewModel.changeColor(Color.Green)
                                    }
                                }
                                bookEntryViewModel.dropDownPosition = index
                            }
                        )

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun DropDownPreview() {
    MyApp {
        //ColorChangeScreen()
    }
}


