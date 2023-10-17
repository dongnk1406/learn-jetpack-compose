package com.example.learncompose.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.learncompose.domain.model.Note

@Database(
    entities = [Note::class], version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDAO

    companion object {
        const val DB_NAME = "note_db"
    }
}