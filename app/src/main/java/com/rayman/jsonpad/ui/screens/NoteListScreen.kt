package com.rayman.jsonpad.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.rayman.jsonpad.data.local.Note
import com.rayman.jsonpad.ui.components.AddNoteButton
import com.rayman.jsonpad.ui.components.NoteTopBar
import com.rayman.jsonpad.ui.components.ToNoteList
import com.rayman.jsonpad.ui.viewmodel.NoteViewModel

@Composable
fun NoteListScreen(
    viewModel: NoteViewModel = hiltViewModel(),
    onAddNote: () -> Unit = {},
    onNoteClick: (Note) -> Unit = {}
) {
    val notes = viewModel.allNotes.collectAsState(emptyList()) // Directly using 'by'
    val selectedNotes = remember { mutableStateListOf<Note>() }

    Scaffold(
        topBar = { NoteTopBar() },
        floatingActionButton = { AddNoteButton(onAddNote) }
    ) { paddingValues ->
        if (notes.value.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No notes available", style = MaterialTheme.typography.headlineLarge)
            }
        } else {
            ToNoteList(
                notes = notes.value,
                selectedNotes = selectedNotes,
                modifier = Modifier.padding(paddingValues),
                onNoteClick = onNoteClick
            )
        }
    }
}


@Preview
@Composable
private fun PreviewNoteListScreen() {
    NoteListScreen()
}

