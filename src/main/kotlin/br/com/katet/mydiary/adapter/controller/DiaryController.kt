package br.com.katet.mydiary.adapter.controller

import br.com.katet.mydiary.adapter.controller.request.NoteRequest
import br.com.katet.mydiary.adapter.controller.response.ClientDiaryInfoResponse
import br.com.katet.mydiary.adapter.database.ClientDiaryInfo
import br.com.katet.mydiary.adapter.database.DiaryRepository
import br.com.katet.mydiary.application.service.DeleteNoteService
import br.com.katet.mydiary.application.service.GetNoteService
import br.com.katet.mydiary.application.service.PostNoteService
import br.com.katet.mydiary.application.service.UpdateNoteService
import kotlinx.coroutines.CoroutineName
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/v1/my-diary/notes")
class DiaryController(
    private val postNoteService: PostNoteService,
    private val getNoteService: GetNoteService,
    private val updateNoteService: UpdateNoteService,
    private val deleteNoteService: DeleteNoteService
) {

    @PostMapping
    suspend fun createNote(@RequestBody noteRequest: NoteRequest): String {
        return postNoteService.execute(noteRequest).also {
            println("Note saved! Thread = ${Thread.currentThread().name}")
        }
    }

    @GetMapping("/users/{userId}/dates/{date}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getNote(
        @PathVariable userId: Int,
        @PathVariable
        @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate
    ): ClientDiaryInfoResponse? {
        return getNoteService.execute(userId, date)?.toResponse()
    }

    @PatchMapping("/users/{userId}/dates/{date}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun updateNote(
        @PathVariable userId: Int,
        @PathVariable
        @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate,
        @RequestBody noteRequest: NoteRequest
    ): String {
        return updateNoteService.execute(userId, date, noteRequest)
    }

    //@RequestParam vs @PathVariable
    @Transactional
    @DeleteMapping("/users/{userId}/dates/{date}")
    suspend fun deleteNote(
        @PathVariable userId: Int,
        @PathVariable
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        date: LocalDate
    ): String {
        return deleteNoteService.execute(userId, date)
    }

    @DeleteMapping("/delete")
    suspend fun deleteAllNotes(): String{
        return deleteNoteService.execute()
    }
}

/*
If a note was created, but already there was it with the same userId and date, the previous note should be updated by the more recent
(the rule by time will be not considerate for now)

https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
 */