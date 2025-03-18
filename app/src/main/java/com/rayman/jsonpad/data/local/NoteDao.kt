package com.rayman.jsonpad.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun getAllByDescCreatedAt(): Flow<List<Note>>

    @Query("SELECT category FROM notes")
    fun getCategories(): Flow<List<String>>

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    suspend fun getNoteById(id: String): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long // Change from Int to Long

    @Transaction
    suspend fun insertAndReturnNote(note: Note): Note {
        val id = insertNote(note)
        return getNoteById(id.toString())!! // Fetch the inserted note
    }

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
}
