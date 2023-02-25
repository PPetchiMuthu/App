package com.example.notes.ui.note

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.model.Note
import com.example.notes.domain.use_case.NoteUseCase
import com.example.notes.domain.util.NoteOrder
import com.example.notes.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state = _state

    private var getNotesJob: Job? = null

    private var temporaryDeletedNode: Note? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onAction(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNode -> {
                viewModelScope.launch {
                    noteUseCase.deleteNote(event.note)
                    temporaryDeletedNode = event.note
                }
            }
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(event.noteOrder)

            }
            NotesEvent.RestoreNode -> {
                viewModelScope.launch {
                    temporaryDeletedNode?.let { noteUseCase.insertNote(it) }
                    temporaryDeletedNode = null
                }
            }
            NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCase.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }
}