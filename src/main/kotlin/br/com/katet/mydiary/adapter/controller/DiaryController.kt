package br.com.katet.mydiary.adapter.controller

import br.com.katet.mydiary.adapter.controller.request.NoteRequest
import br.com.katet.mydiary.adapter.controller.response.NoteResponse
import br.com.katet.mydiary.adapter.controller.response.NotesResponse
import br.com.katet.mydiary.adapter.database.toResponse
import br.com.katet.mydiary.application.service.*
import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

private val log = LoggerFactory.getLogger(DiaryController::class.java)

@RestController
@RequestMapping("/v1/my-diary")
class DiaryController(
    private val postNoteService: PostNoteService,
    private val getAllUsersService: GetAllUsersService,
    private val getNoteService: GetNoteService,
    private val updateNoteService: UpdateNoteService,
    private val deleteNoteService: DeleteNoteService
) {

    private var count = 0

    @GetMapping("/users", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllUsers(): ResponseEntity<List<Int>> {
        log.info("Getting user's id")

        val response = getAllUsersService.execute()

        if (response.isEmpty())
            return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity.ok(response).also {
            log.info("Finish search of all users")

            count += 1

            log.info("noteCreated: $count")
        }
    }

    @PostMapping("/notes")
    suspend fun createNote(
        @RequestHeader userId: Int,
        @RequestBody note: String): ResponseEntity<String> {
        log.info("Creating note")

        return when (val response = postNoteService.execute(userId, note)) {
            "Error" -> ResponseEntity(response, HttpStatus.UNPROCESSABLE_ENTITY)
            else -> {
                ResponseEntity.ok(response).also {
                    log.info("Note created")
                }
            }
        }
    }

    @GetMapping("/notes", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllNotes(
        @RequestHeader userId: Int
    ): ResponseEntity<NotesResponse> {
        log.info("Getting note")

        val response = getNoteService.execute(userId)

        if (response.isEmpty())
            return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity.ok(NotesResponse(response.toResponse())).also {
            log.info("Finish search of a note")
        }
    }


    @GetMapping("/notes", params = ["id"], produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getNote(
        @RequestHeader userId: Int,
        @RequestParam id: String
    ): ResponseEntity<NoteResponse> {
        log.info("Getting note")

        val response = getNoteService.execute(userId, id) ?: return ResponseEntity<NoteResponse>(HttpStatus.NOT_FOUND)

        return ResponseEntity.ok(response.toResponse()).also {
            log.info("Finish search of a note")
        }
    }

    @PatchMapping("/notes",
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun updateNote(
        @RequestHeader userId: Int,
        @RequestBody noteRequest: NoteRequest
    ): ResponseEntity<String> {
        log.info("Updating note")

        return when(val response = updateNoteService.execute(userId, noteRequest)){
            "Note not found" -> ResponseEntity(HttpStatus.NOT_FOUND)
            "Error" -> ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY)
            else -> ResponseEntity.ok(response)
        }.also {
            log.info("Note updated")
        }
    }

    //@RequestParam vs @PathVariable
    @DeleteMapping("/notes", params = ["id"])
    suspend fun deleteNote(
        @RequestHeader userId: Int,
        @RequestParam id: String
    ): String {
        log.info("Delete note")

        return deleteNoteService.execute(userId, id).also {
            log.info("Note deleted")
        }
    }

    @DeleteMapping("/notes")
    suspend fun deleteAllNotes(): String {
        return deleteNoteService.execute().also {
            log.info(it)
        }
    }
}
