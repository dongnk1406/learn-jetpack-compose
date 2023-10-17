package com.example.learncompose.domain.use_cases

data class NoteUseCases(
    val getListNotes: GetListNotes,
    val getNoteById: GetNoteById,
    val addNote: AddNote,
    val updateNote: UpdateNote,
    val deleteNote: DeleteNote,
)