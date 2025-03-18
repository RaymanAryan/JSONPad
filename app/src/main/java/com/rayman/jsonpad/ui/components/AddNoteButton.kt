package com.rayman.jsonpad.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun AddNoteButton(onAddNote: () -> Unit) {
    FloatingActionButton(onClick = onAddNote) {
        Icon(Icons.Default.Add, contentDescription = "Add Note")
    }
}