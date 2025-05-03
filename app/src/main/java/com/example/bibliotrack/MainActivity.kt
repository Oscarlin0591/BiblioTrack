package com.example.bibliotrack

/*
BiblioTrack
Author: Jacob Levin & Oscar Lin
04/12/2025
BiblioTrack is an app to keep track of your reads
and share with your friends what books you've been
reading
 */

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bibliotrack.navigation.BookNavigation
import com.example.bibliotrack.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            AppTheme{
                Surface(tonalElevation = 5.dp){
                    MyApp {
                        BookNavigation()
                    }
                }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    AppTheme {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Surface (tonalElevation = 5.dp){
            MyApp {
                BookNavigation()
            }
        }
    }
}