package com.example.learncompose.domain.repository

import com.example.learncompose.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getListNotes(): Flow<List<Note>>

    suspend fun addNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun getNoteById(id: Int): Note?
}