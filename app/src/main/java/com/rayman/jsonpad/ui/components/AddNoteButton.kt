package com.rayman.jsonpad.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AddNoteButton(onAddNote: () -> Unit) {
    FloatingActionButton(onClick = onAddNote, containerColor = MaterialTheme.colorScheme.primary) {
        Icon(Icons.Default.Add, contentDescription = "Add Note")
    }
}