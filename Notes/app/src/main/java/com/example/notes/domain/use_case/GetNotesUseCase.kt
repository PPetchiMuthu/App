package com.example.notes.domain.use_case

import com.example.notes.domain.model.Note
import com.example.notes.domain.repository.NoteRepository
import com.example.notes.domain.util.NoteOrder
import com.example.notes.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder.orderType) {
                OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Date -> notes.sortedBy { it.timeStamp }
                        is NoteOrder.Title -> notes.sortedBy { it.title }
                    }
                }
                OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Date -> notes.sortedByDescending { it.timeStamp }
                        is NoteOrder.Title -> notes.sortedByDescending { it.title }
                    }
                }
            }
        }
    }


}
