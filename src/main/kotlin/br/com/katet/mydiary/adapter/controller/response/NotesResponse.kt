package br.com.katet.mydiary.adapter.controller.response

import br.com.katet.mydiary.adapter.database.Note
import java.time.LocalDateTime

class NotesResponse(
    val notes: List<Note>
) {
    class Note(
        val id: String,
        val timestamp: LocalDateTime,
        val note: String
    )
}