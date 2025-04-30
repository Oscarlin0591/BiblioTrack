package com.example.bibliotrack.screens

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bibliotrack.MyApp
import com.example.bibliotrack.R
import com.example.bibliotrack.model.BookEntryViewModel
import com.example.bibliotrack.model.BookViewModel
import com.example.bibliotrack.navigation.AppBar
import com.example.bibliotrack.navigation.AppScreens
import com.example.bibliotrack.navigation.NavigationDestination
import com.example.bibliotrack.navigation.PlainBar

//object SettingDestination : NavigationDestination {
//    override val route = "SettingScreen"
//    override val titleRes = R.string.setting_screen
//    const val itemIdArg = "itemId"
//    val routeWithArgs = "$route/{$itemIdArg}"
//}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun ColorChangeScreen(
//    navigateToHomeScreen: (Int) -> Unit,
    navController: NavController,
    bookEntryViewModel: BookEntryViewModel
) {
    val isExpanded = remember { mutableStateOf(false) }
    val colors = listOf("Light","Dark", "Default", "Yellow", "Purple", "Green")
    val defaultColor = MaterialTheme.colorScheme.primaryContainer
    @Composable
    fun DropDown() {
        Column {
            Row (modifier = Modifier.clickable { isExpanded.value = true }){
                Text(colors[bookEntryViewModel.dropDownPosition])
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
                textToShare = "",
                context = LocalContext.current,
                modifier = Modifier
            )
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
                                Text(text = color)
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

            //white button
            /*Card(onClick = {/*bookViewModel.updateColor(color = Color.White)*/},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Box(Modifier
                    .background(Color.White)
                    .weight(1f)
                    .height(50.dp)
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)){

                }
                Text(text = "White", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            //black button
            Card(onClick = {/*bookViewModel.updateColor(color = Color.Black)*/},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Box(Modifier
                    .background(Color.Black)
                    .weight(1f)
                    .height(50.dp)
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)){

                }
                Text(text = "Black", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            //gray button
            Card(onClick = {/*bookViewModel.updateColor(color = Color.Gray)*/},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Box(Modifier
                    .background(Color.Gray)
                    .weight(1f)
                    .height(50.dp)
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)){

                }
                Text(text = "Gray", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            //green button
            Card(onClick = {/*bookViewModel.updateColor(color = Color.Green)*/},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Box(Modifier
                    .background(Color.Green)
                    .weight(1f)
                    .height(50.dp)
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)){

                }
                Text(text = "Green", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            //magenta button
            Card(onClick = {/*bookViewModel.updateColor(color = Color.Magenta)*/},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Box(Modifier
                    .background(Color.Magenta)
                    .weight(1f)
                    .height(50.dp)
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)){

                }
                Text(text = "Magenta", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            //yellow button
            Card(onClick = {/*bookViewModel.updateColor(color = Color.Yellow)*/},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Box(Modifier
                    .background(Color.Yellow)
                    .weight(1f)
                    .height(50.dp)
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)){

                }
                Text(text = "Yellow", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }*/


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


