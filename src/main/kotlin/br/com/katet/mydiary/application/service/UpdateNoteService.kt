package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.controller.request.NoteRequest
import br.com.katet.mydiary.adapter.database.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private val log = LoggerFactory.getLogger(UpdateNoteService::class.java)

@Service
class UpdateNoteService(private val repository: DiaryRepository) {

    suspend fun execute(userId: Int, noteRequest: NoteRequest): String {

        log.info("Starting to update note in database... ")

        try {
            val noteInfo = withContext(Dispatchers.IO) {
                log.info("Searching if note already exists in database... ")
                repository.findByUserIdAndId(userId = userId, id = noteRequest.id)
            } ?: return "Note not found"

            noteInfo.apply {
                this.note = noteRequest.note
                this.timestamp = LocalDateTime.now()
            }.also { note ->
                withContext(Dispatchers.IO) {
                    repository.save(note)
                }
            }
        }catch (ex: Exception){
            return "Error"
        }

        return "Note updated!"
    }
}