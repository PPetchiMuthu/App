package com.example.notes.domain.use_case

import com.example.notes.domain.model.Note
import com.example.notes.domain.repository.NoteRepository

class GetNoteUseCase (private val repository: NoteRepository) {
    suspend operator fun invoke(id: Int) : Note?{
        return repository.getNoteById(id)
    }
}