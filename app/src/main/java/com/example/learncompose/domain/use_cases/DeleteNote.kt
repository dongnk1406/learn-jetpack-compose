package com.example.learncompose.domain.use_cases

import com.example.learncompose.domain.model.Note
import com.example.learncompose.domain.repository.NoteRepository

class DeleteNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        return noteRepository.deleteNote(note)
    }
}