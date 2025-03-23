package com.rayman.jsonpad.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
    val isLoading = viewModel.isLoading.collectAsState().value
    val notes = viewModel.currentNotes.collectAsState().value

//    LaunchedEffect(key1 = category) {
//        Log.d("Completed1", category.toString())
//        viewModel.loadNotes()
//    }


    val selectedNotes = viewModel2.selectedList.collectAsState().value
    Log.d("Complete2",notes.toString())

    Scaffold(
        topBar = {
            NoteTopBar(
                selectedNotes = selectedNotes,
                viewModel = viewModel,
                viewModel2 = viewModel2,
                allNotes = notes,
                navDrawerState = navDrawerState
            )
        },
        floatingActionButton = { AddNoteButton(onAddNote) }
    ) { paddingValue ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize(.04f))
            }
        } else {
            ToNoteList(
                notes = notes,
                selectedNotes = selectedNotes,
                modifier = Modifier.padding(paddingValue),
                onNoteClick = onNoteClick
            )
        }
    }
}
