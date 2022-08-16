package br.com.katet.mydiary.note

import java.time.LocalDate
import java.time.LocalTime


class ClientDiaryInfoResponse(
     val id: String,
     val userId: Int,
     val date: LocalDate,
     val time: LocalTime,
     val notes: String
)