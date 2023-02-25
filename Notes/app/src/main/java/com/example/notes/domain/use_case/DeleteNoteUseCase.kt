package com.example.notes.domain.use_case

import com.example.notes.domain.model.Note
import com.example.notes.domain.repository.NoteRepository

class DeleteNoteUseCase(private val repository: NoteRepository) {

    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}