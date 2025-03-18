package com.rayman.jsonpad.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rayman.jsonpad.data.local.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    note: Note,
    isSelected: Boolean,
    onLongPress: () -> Unit,
    onClick: () -> Unit
) {
    val createdDate = formatTimestamp(note.createdAt)
    val updatedDate = formatTimestamp(note.updatedAt)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = onClick, // Navigate to Edit
                onLongClick = onLongPress // Select/Deselect Note
            )
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color.Red else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = note.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = note.content, style = MaterialTheme.typography.bodyMedium, maxLines = 3)
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Created: $createdDate",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Text(
                    text = "Updated: $updatedDate",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

/**
 * Converts a timestamp (Long) to a human-readable date string.
 */
fun formatTimestamp(timestamp: Long?): String {
    if (timestamp == null) return "Unknown"

    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()) // "07 Jul 2024, 05:30 PM"
    return sdf.format(Date(timestamp))
}

@Preview(showBackground = true)
@Composable
private fun PreviewNoteItem() {
    val sampleNote = Note(
        id = 123,
        title = "Sample Note",
        content = "This is a preview note content.",
        category = "Personal",
        createdAt = System.currentTimeMillis() - 86400000, // 1 day ago
        updatedAt = System.currentTimeMillis()
    )

    NoteItem(
        note = sampleNote,
        isSelected = false,
        onLongPress = {},
        onClick = {}
    )
}
