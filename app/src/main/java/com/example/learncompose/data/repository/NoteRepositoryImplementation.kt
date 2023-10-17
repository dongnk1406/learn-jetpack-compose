package com.example.learncompose.data.repository

import com.example.learncompose.data.data_source.NoteDAO
import com.example.learncompose.domain.model.Note
import com.example.learncompose.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImplementation(private val noteDao: NoteDAO) : NoteRepository {
    override fun getListNotes(): Flow<List<Note>> {
        return noteDao.getListNotes()
    }

    override suspend fun addNote(note: Note) {
        return noteDao.addNote(note)
    }

    override suspend fun updateNote(note: Note) {
        return noteDao.updateNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return noteDao.deleteNote(note)
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }

}