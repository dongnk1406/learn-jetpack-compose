package com.example.learncompose.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.learncompose.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {
    @Query("select * from note")
    fun getListNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("select * from note where id=:id")
    suspend fun getNoteById(id: Int): Note?
}