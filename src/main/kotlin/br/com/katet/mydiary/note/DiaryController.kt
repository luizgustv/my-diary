package br.com.katet.mydiary.note

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/v1/my-diary/notes")
class DiaryController(
    private val repository: DiaryRepository
) {

    @PostMapping
    fun createNote(@RequestBody noteRequest: NoteRequest): String {
        repository.save(
            ClientDiaryInfo(userId = noteRequest.userId, notes = noteRequest.notes)
        )
        return "Salvo!"
    }

    @GetMapping("/users/{userId}/dates/{date}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getNote(
        @PathVariable userId: Int,
        @PathVariable
        @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate
    ): ClientDiaryInfoResponse {
        return repository.findByUserIdAndDate(userId = userId, date = date).toResponse()
    }

    //@RequestParam  vs @PathVariable
    @Transactional
    @DeleteMapping("/users/{userId}/dates/{date}")
    fun deleteNote(
        @PathVariable userId: Int,
        @PathVariable
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        date: LocalDate
    ): String {
        repository.deleteByUserIdAndDate(userId = userId, date = date)
        return "Deletado!"
    }
}

/*
If a note was created, but already there was it with the same userId and date, the previous note should be updated by the more recent
(the rule by time will be not considerate for now)
 */