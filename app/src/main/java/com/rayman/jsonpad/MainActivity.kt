package com.rayman.jsonpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.rayman.jsonpad.ui.navigation.NoteNavGraph
import com.rayman.jsonpad.ui.theme.JSONPadTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JSONPadTheme {
                App()
            }
        }
    }
}
@Composable
fun App() {
    val navController = rememberNavController()
    NoteNavGraph(navController)
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    JSONPadTheme {
//        Greeting("Android")
//    }
//}