package com.example.notes.ui.note

import com.example.notes.domain.model.Note
import com.example.notes.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNode(val note: Note) : NotesEvent()
    object RestoreNode : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}
