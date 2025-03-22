package com.rayman.jsonpad.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.rayman.jsonpad.data.local.Note
import com.rayman.jsonpad.ui.viewmodel.NoteViewModel
import com.rayman.jsonpad.ui.viewmodel.SelectNoteViewModel

@Composable
fun NoteListScreen(
    viewModel: NoteViewModel = hiltViewModel(),
    viewModel2: SelectNoteViewModel = hiltViewModel(),
    onAddNote: () -> Unit = {},
    onNoteClick: (Note) -> Unit = {},
    navDrawerState: DrawerState
) {
    val isWorking = rememberSaveable { mutableStateOf(true) }
    val category = viewModel.currentCategory.collectAsState(null).value
    var notes = viewModel.currentNotes.collectAsState(emptyList<Note>()).value
    LaunchedEffect(key1 = category) {
        isWorking.value = true
        category?.let {
            viewModel.setNotesByCategory(category)
            }
        isWorking.value = false
    }

    val selectedNotes = viewModel2.selectedList.collectAsState().value
    Log.d("Complete2",notes.toString())

    Scaffold(
        topBar = {
            NoteTopBar(
                selectedNotes = selectedNotes,
                viewModel = viewModel,
                viewModel2 = viewModel2,
                allNotes = if(category == null) viewModel.allNotes.collectAsState(emptyList<Note>()).value else notes,
                navDrawerState = navDrawerState
            )
        },
        floatingActionButton = { AddNoteButton(onAddNote) }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            if (isWorking.value) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier)
                }
            } else {
                ToNoteList(
                    notes = if(category == null) viewModel.allNotes.collectAsState(null).value else notes,
                    selectedNotes = selectedNotes,
                    modifier = Modifier.padding(paddingValues),
                    onNoteClick = onNoteClick
                )
            }
        }
    }
}
