package br.com.katet.mydiary.adapter.controller.response

import java.time.LocalDateTime
class NoteResponse(
     val id: String,
     val date: LocalDateTime,
     val note: String
)