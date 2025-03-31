package com.rayman.jsonpad.data.repository

import com.rayman.jsonpad.data.local.Note
import com.rayman.jsonpad.data.local.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(private val noteDao: NoteDao) {
    val allNotes: Flow<List<Note>> = noteDao.getAllByDescCreatedAt() // Using the sorted query
    val allCategories: Flow<List<String>> = noteDao.getCategories()

    suspend fun addNote(note: Note): Note {
        return noteDao.insertAndReturnNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}

