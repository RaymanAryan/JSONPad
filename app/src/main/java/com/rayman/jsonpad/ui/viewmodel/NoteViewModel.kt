package com.rayman.jsonpad.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayman.jsonpad.data.local.Note
import com.rayman.jsonpad.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    private val _currentCategory = MutableStateFlow<String?>(null)
    val currentCategory: StateFlow<String?> get() = _currentCategory  // ✅ Correct way to expose StateFlow

    fun setCategory(category: String?) {
        _currentCategory.value = category  // ✅ Correct way to update MutableStateFlow
    }

    private val _currentNotes = MutableStateFlow<List<Note>>(emptyList()) // ✅ MutableStateFlow for current notes
    val currentNotes: StateFlow<List<Note>> get() = _currentNotes

    val allNotes: StateFlow<List<Note>?> = repository.allNotes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
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
        note.apply {
            this.title = title
            this.content = content
            this.category = category
            this.updatedAt = System.currentTimeMillis()
        }
        repository.updateNote(note)
    }

    suspend fun addNote(title: String, content: String, category: String): Note {
        return repository.addNote(title, content, category)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun setNotesByCategory(category: String?) {
            viewModelScope.launch {
                repository.allNotes
                    .map { notes ->
                        notes.filter { it.category == category }
                    }
                    .collect { filteredNotes ->
                        _currentNotes.value = filteredNotes
                    }
            }
    }
}


