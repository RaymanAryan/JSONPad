package com.rayman.jsonpad.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities =  [Note::class], version = 1, exportSchema = true)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}