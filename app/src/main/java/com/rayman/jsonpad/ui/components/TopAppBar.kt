package com.rayman.jsonpad.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import com.rayman.jsonpad.data.local.Note
import com.rayman.jsonpad.ui.viewmodel.NoteViewModel
import com.rayman.jsonpad.ui.viewmodel.SelectNoteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTopBar(
    viewModel: NoteViewModel,
    viewModel2: SelectNoteViewModel,
    allNotes: List<Note>?, selectedNotes: List<Note>, navDrawerState: DrawerState
) {
    if(allNotes != null){
        val coroutine = rememberCoroutineScope()
        TopAppBar(
            title = { Text(if (selectedNotes.isEmpty()) "My Notes" else "${selectedNotes.size} Selected") },
            navigationIcon = {
                if (selectedNotes.isEmpty()) {
                    IconButton(onClick = { coroutine.launch { navDrawerState.open() }}) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                } else {
                    IconButton(onClick = { viewModel2.clearSelection() }) {
                        Icon(Icons.Default.Close, contentDescription = "Deselect All")
                    }
                }
            },
            actions = {
                if (selectedNotes.isNotEmpty()) {
                    IconButton(onClick = {
                        selectedNotes.forEach { viewModel.deleteNote(it) }
                        viewModel2.clearSelection()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Selected Notes")
                    }
                    IconButton(onClick = {
                        if (selectedNotes.size == allNotes.size) {
                            viewModel2.clearSelection()
                        } else {
                            viewModel2.selectAll(allNotes)
                        }
                    }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedNotes.size == allNotes.size,
                                onClick = {
                                    if (selectedNotes.size == allNotes.size) {
                                        viewModel2.clearSelection()
                                    } else {
                                        viewModel2.selectAll(allNotes)
                                    }
                                }
                            )
                            Text("Select All")
                        }
                    }
                }
            }
        )
    }
}




//@Preview
//@Composable
//private fun PreviewNoteTopBar() {
//    NoteTopBar()
//}