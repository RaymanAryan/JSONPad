package com.rayman.jsonpad.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayman.jsonpad.data.local.Note
import com.rayman.jsonpad.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    val allNotes: StateFlow<List<Note>> = repository.allNotes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    val allCategories: StateFlow<List<String>> = repository.allCategories.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    fun getNoteById(id: String): Flow<Note?> {
        return repository.allNotes
            .map { notes -> notes.find { it.id.toString() == id } }
    }

    fun updateNote(note: Note, title: String, content: String, category: String) = viewModelScope.launch {
        note.apply { this.title = title
        this.content = content
        this.category = category
        note.updatedAt = System.currentTimeMillis()
        }
        repository.updateNote(note) }

    suspend fun addNote(title: String, content: String, category: String): Note {
        return repository.addNote(title, content, category)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }
}
