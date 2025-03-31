package com.rayman.jsonpad.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rayman.jsonpad.data.local.Note
import com.rayman.jsonpad.ui.viewmodel.SelectNoteViewModel

@Composable
fun ToNoteList(
    selectNoteViewModel: SelectNoteViewModel,
    notes: List<Note>,
    selectedNotes: List<Note>, // Should store individual notes, not lists
    modifier: Modifier,
    onNoteClick: (Note) -> Unit
) {
    when {
        notes.isNotEmpty() -> {
            val selectedNotesSet = selectedNotes.toSet() // Optimize lookups
            Surface{
                LazyColumn(modifier = modifier) {
                    items(notes) { note ->
                        NoteItem(
                            note = note,
                            isSelected = selectedNotesSet.contains(note),
                            onLongPress = { selectNoteViewModel.toggleSelection(note) },
                            onClick = { onNoteClick(note) },
                            selectNoteViewModel = selectNoteViewModel
                        )
                    }
                }
            }
        }
        else -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp), // Better spacing
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No notes available",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


