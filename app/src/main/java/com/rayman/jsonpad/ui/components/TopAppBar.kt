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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rayman.jsonpad.data.local.Note
import com.rayman.jsonpad.ui.viewmodel.NoteViewModel
import com.rayman.jsonpad.ui.viewmodel.SelectNoteViewModel
import com.rayman.jsonpad.ui.viewmodel.UIViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTopBar(
    noteViewModel: NoteViewModel,
    selectNoteViewModel: SelectNoteViewModel,
    allNotes: List<Note>, selectedNotes: List<Note>, navDrawerState: DrawerState,
    uiViewModel: UIViewModel,
) {     val coroutine = rememberCoroutineScope()
        TopAppBar(modifier = Modifier, colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
            title = { Text(if (selectedNotes.isEmpty()) "My Notes" else "${selectedNotes.size} Selected") },
            navigationIcon = {
                if (selectedNotes.isEmpty()) {
                    IconButton(onClick = { coroutine.launch { navDrawerState.open() }}) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                } else {
                    IconButton(onClick = { selectNoteViewModel.clearSelection() }) {
                        Icon(Icons.Default.Close, contentDescription = "Deselect All")
                    }
                }
            },
            actions = {
                if (selectedNotes.isNotEmpty()) {
                    IconButton(onClick = {
                        selectedNotes.forEach { noteViewModel.deleteNote(it) }
                        selectNoteViewModel.clearSelection()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Selected Notes")
                    }
                    IconButton(onClick = {
                        if (selectedNotes.size == allNotes.size) {
                            selectNoteViewModel.clearSelection()
                        } else {
                            selectNoteViewModel.selectAll(allNotes)
                        }
                    }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(colors = RadioButtonColors(
                                selectedColor =
                                    MaterialTheme.colorScheme.tertiary,
                                unselectedColor =
                                    MaterialTheme.colorScheme.tertiaryContainer,
                                disabledSelectedColor =
                                    MaterialTheme.colorScheme.secondary,
                                disabledUnselectedColor =
                                    MaterialTheme.colorScheme.secondaryContainer
                            ),
                                selected = selectedNotes.size == allNotes.size,
                                onClick = {
                                    if (selectedNotes.size == allNotes.size) {
                                        selectNoteViewModel.clearSelection()
                                    } else {
                                        selectNoteViewModel.selectAll(allNotes)
                                    }
                                }
                            )
                            Text("Select All")
                        }
                    }
                }
                else {
                    MoreOptionsMenu(uiViewModel)
                }
            }
        )
}



