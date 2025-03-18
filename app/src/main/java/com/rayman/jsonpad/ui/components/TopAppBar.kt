package com.rayman.jsonpad.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTopBar() {
    TopAppBar(
        title = { Text("My Notes") },
        navigationIcon = {
            IconButton(onClick = { /* TODO: Implement Search */ }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
}

@Preview
@Composable
private fun PreviewNoteTopBar() {
    NoteTopBar()
}