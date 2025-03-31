package com.rayman.jsonpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.rayman.jsonpad.ui.navigation.NoteNavGraph
import com.rayman.jsonpad.ui.theme.JSONPadTheme
import com.rayman.jsonpad.ui.viewmodel.JsonViewModel
import com.rayman.jsonpad.ui.viewmodel.NoteViewModel
import com.rayman.jsonpad.ui.viewmodel.SelectNoteViewModel
import com.rayman.jsonpad.ui.viewmodel.UIViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App(
                noteViewModel = hiltViewModel(),
                selectNoteViewModel = hiltViewModel(),
                jsonViewModel = hiltViewModel(),
                uiViewModel = hiltViewModel()
            )
        }
    }
}

@Composable
fun App(
    noteViewModel: NoteViewModel,
    selectNoteViewModel: SelectNoteViewModel,
    jsonViewModel: JsonViewModel,
    uiViewModel: UIViewModel
) {
    JSONPadTheme(darkTheme = uiViewModel.isDarkMode.collectAsState().value, uiViewModel = uiViewModel, content = {
        val navController = rememberNavController()
        NoteNavGraph(
            navController = navController,
            noteViewModel = noteViewModel,
            selectNoteViewModel = selectNoteViewModel,
            jsonViewModel = jsonViewModel,
            uiViewModel = uiViewModel
        )
    })
}
