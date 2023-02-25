package com.example.notes.domain.use_case

data class NoteUseCase(
    val getNotes: GetNotesUseCase,
    val deleteNote: DeleteNoteUseCase,
    val insertNote : InsertUseCase,
    val getNote : GetNoteUseCase
)