package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.controller.request.NoteRequest
import br.com.katet.mydiary.adapter.database.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

private val log = LoggerFactory.getLogger(UpdateNoteService::class.java)

@Service
class UpdateNoteService(private val repository: DiaryRepository) {

    suspend fun execute(userId: Int, date: LocalDate, noteRequest: NoteRequest): String {

        log.info("Starting to get note in database... ")

        withContext(Dispatchers.IO) {
            log.info("Searching if note already exists in database... ")
            repository.findByUserIdAndDate(userId = userId, date = date)
        }
            ?.apply { this.notes = noteRequest.notes }?.also { clientDiaryInfo ->
                withContext(Dispatchers.IO) {
                    repository.save(clientDiaryInfo)
                }
            }

        return "Note updated!"
    }
}