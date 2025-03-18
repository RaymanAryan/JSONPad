package com.rayman.jsonpad.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rayman.jsonpad.data.local.Note

@Composable
fun ToNoteList(
    notes: List<Note>,
    selectedNotes: MutableList<Note>,
    modifier: Modifier = Modifier,
    onNoteClick: (Note) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(notes) { note ->
            NoteItem(
                note = note,
                isSelected = selectedNotes.contains(note),
                onLongPress = {
                    if (selectedNotes.contains(note)) selectedNotes.remove(note)
                    else selectedNotes.add(note)
                },
                onClick = { onNoteClick(note) }
            )
        }
    }
}