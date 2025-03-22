package com.rayman.jsonpad.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayman.jsonpad.data.local.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectNoteViewModel @Inject constructor() : ViewModel() {
    private val _selectedList = MutableStateFlow<List<Note>>(emptyList())
    val selectedList: StateFlow<List<Note>> = _selectedList.asStateFlow()

    fun toggleSelection(note: Note) {
        viewModelScope.launch {
            _selectedList.update { currentList ->
                if (currentList.contains(note)) {
                    currentList - note  // Deselect note
                } else {
                    currentList + note  // Select note
                }
            }
        }
    }

    fun clearSelection() {
        viewModelScope.launch {
            _selectedList.value = emptyList()  // Deselect all notes
        }
    }

    fun selectAll(notes: List<Note>) {
        viewModelScope.launch {
            _selectedList.value = notes  // Select all notes
        }
    }

    fun isSelected(note: Note): Boolean {
        return _selectedList.value.contains(note)
    }
}

