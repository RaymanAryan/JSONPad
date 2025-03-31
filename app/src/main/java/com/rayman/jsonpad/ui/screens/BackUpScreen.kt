package com.rayman.jsonpad.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.rayman.jsonpad.ui.components.JsonScreen
import com.rayman.jsonpad.ui.components.SimpleBar
import com.rayman.jsonpad.ui.viewmodel.JsonViewModel
import com.rayman.jsonpad.ui.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackUpScreen(
    title: String,
    navigate: NavHostController,
    jsonViewModel: JsonViewModel,
    noteViewModel: NoteViewModel
) {
    val coroutine = rememberCoroutineScope()

    Scaffold(
        topBar = {
            SimpleBar(title = title, navigate = navigate, coroutine = coroutine)
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            JsonScreen(noteViewModel = noteViewModel, jsonViewModel = jsonViewModel)
        }
    }
}
