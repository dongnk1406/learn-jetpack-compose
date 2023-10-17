package com.example.learncompose.domain.use_cases

import com.example.learncompose.domain.model.Note
import com.example.learncompose.domain.repository.NoteRepository

class GetNoteById(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(id: Int): Note? {
        return noteRepository.getNoteById(id)
    }
}