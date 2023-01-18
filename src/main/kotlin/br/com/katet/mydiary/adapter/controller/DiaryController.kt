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

    @GetMapping("/users", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllUsers(): ResponseEntity<List<Int>> {
        log.info("Getting user's id")

        val response = getAllUsersService.execute()

        if (response.isEmpty())
            return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity.ok(response).also {
            log.info("Finish search of all users")
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

    @PatchMapping("/notes", produces = [MediaType.APPLICATION_JSON_VALUE])
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

/*
https://github.com/grafana/tutorial-environment/blob/master/docker-compose.yml

https://grafana.com/tutorials/grafana-fundamentals/

https://docs.spring.io/spring-boot/docs/current/actuator-api/htmlsingle/#metrics
 */

/*
If a note was created, but already there was it with the same userId and date, the previous note should be updated by the more recent
(the rule by time will be not considerate for now)

https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
 */

/*
grafana
- understand
- create dash
- use grafana + loki
- create scenarios in wiremock in order to see this in a error dashboard
- create custom metric

 https://www.tigera.io/learn/guides/prometheus-monitoring/prometheus-metrics/

custom metric
metric for each endpoint - count
prometheus and jobs

 */