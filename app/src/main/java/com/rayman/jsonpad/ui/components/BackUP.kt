package com.rayman.jsonpad.ui.components

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.rayman.jsonpad.ui.viewmodel.JsonViewModel
import com.rayman.jsonpad.ui.viewmodel.NoteViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@Composable
fun JsonScreen(
    noteViewModel: NoteViewModel,
    jsonViewModel: JsonViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var fileUri by remember { mutableStateOf<Uri?>(null) }

    // ✅ Export JSON Launcher (No permission required)
    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        uri?.let {
            fileUri = it
            coroutineScope.launch {
                val notes = noteViewModel.getAllNotes().firstOrNull() ?: emptyList()
                jsonViewModel.saveJson(it, notes)
                Toast.makeText(context, "Export successful!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ✅ Import JSON Launcher (Works on Oreo+)
    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            fileUri = it
            coroutineScope.launch {
                try {
                    val notes = jsonViewModel.loadJson(it)
                    if (notes != null) {
                        notes.forEach { note ->
                            noteViewModel.importNoteFromJson(note)
                        }
                        Toast.makeText(context, "Import successful!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Invalid JSON format.", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Failed to import: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                importLauncher.launch("*/*")
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "JSON Import/Export",
            style = MaterialTheme.typography.headlineMedium,
            textDecoration = TextDecoration.Underline
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement =Arrangement.Center
        ) {
            Button(onClick = { exportLauncher.launch("notes_backup.json") }) {
                Text("Export JSON")
            }

            Spacer(modifier = Modifier.fillMaxWidth(.1f))

            Button(onClick = {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) { // Android 9 and below
                    permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                } else {
                    importLauncher.launch("*/*")
                }
            }) {
                Text("Import JSON")
            }
        }
    }
}