package com.example.notes.domain.repository

import com.example.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?
}