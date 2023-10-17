package com.example.learncompose.domain.use_cases

import com.example.learncompose.domain.model.Note
import com.example.learncompose.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetListNotes(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(): Flow<List<Note>> {
        return noteRepository.getListNotes()
    }
}