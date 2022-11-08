package br.com.katet.mydiary.adapter.controller

import br.com.katet.mydiary.adapter.controller.request.NoteRequest
import br.com.katet.mydiary.adapter.controller.response.ClientDiaryInfoResponse
import br.com.katet.mydiary.application.service.DeleteNoteService
import br.com.katet.mydiary.application.service.GetNoteService
import br.com.katet.mydiary.application.service.PostNoteService
import br.com.katet.mydiary.application.service.UpdateNoteService
import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

private val log = LoggerFactory.getLogger(DiaryController::class.java)

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
        log.info("Creating note")

        return postNoteService.execute(noteRequest).also {
            log.info("Note created")
        }
    }

    @GetMapping("/users/{userId}/dates/{date}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getNote(
        @PathVariable userId: Int,
        @PathVariable
        @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate
    ): ClientDiaryInfoResponse? {
        log.info("Getting note")

        return getNoteService.execute(userId, date)?.toResponse().also {
            log.info("Finish search of a note")
        }
    }

    @PatchMapping("/users/{userId}/dates/{date}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun updateNote(
        @PathVariable userId: Int,
        @PathVariable
        @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate,
        @RequestBody noteRequest: NoteRequest
    ): String {
        log.info("Updating note")

        return updateNoteService.execute(userId, date, noteRequest).also {
            log.info("Note updated")
        }
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
        log.info("Delete note")

        return deleteNoteService.execute(userId, date).also {
            log.info("Note deleted")
        }
    }

    @DeleteMapping("/delete")
    suspend fun deleteAllNotes(): String{
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

 */