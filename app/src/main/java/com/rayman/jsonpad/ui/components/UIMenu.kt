package com.rayman.jsonpad.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.rayman.jsonpad.ui.viewmodel.UIViewModel

@Composable
fun MoreOptionsMenu(uiViewModel: UIViewModel) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val colorSchemes = rememberSaveable { listOf(
        "Normal",
        "MediumContrast",
        "HighContrast",
    ) }
// List of strings

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "More options"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            colorSchemes.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        expanded = false
                        // Handle the option click
                        uiViewModel.changeTheme(option)
                    }
                )
            }
            DropdownMenuItem(
                text = { Text(if (uiViewModel.isDarkMode.collectAsState().value) "Light Mode" else "Dark Mode") },
                onClick = {
                    uiViewModel.toggleDarkMode() // This function should toggle the dark mode state
                }
            )
        }
    }
}
