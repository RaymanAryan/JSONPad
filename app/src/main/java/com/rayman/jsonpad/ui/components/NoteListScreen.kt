package com.rayman.jsonpad.ui.components

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
import com.rayman.jsonpad.data.local.Note
import com.rayman.jsonpad.ui.viewmodel.NoteViewModel
import com.rayman.jsonpad.ui.viewmodel.SelectNoteViewModel
import com.rayman.jsonpad.ui.viewmodel.UIViewModel

@Composable
fun NoteListScreen(
    noteViewModel: NoteViewModel,
    selectNoteViewModel: SelectNoteViewModel,
    onAddNote: () -> Unit = {},
    onNoteClick: (Note) -> Unit = {},
    navDrawerState: DrawerState,
    uiViewModel: UIViewModel
) {
    val isLoading = noteViewModel.isLoading.collectAsState().value
    val notes = noteViewModel.currentNotes.collectAsState().value

    val selectedNotes = selectNoteViewModel.selectedList.collectAsState().value
    Scaffold(
        topBar = {
            NoteTopBar(
                selectedNotes = selectedNotes,
                noteViewModel = noteViewModel,
                uiViewModel = uiViewModel,
                selectNoteViewModel = selectNoteViewModel,
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
                onNoteClick = onNoteClick,
                selectNoteViewModel = selectNoteViewModel
            )
        }
    }
}
