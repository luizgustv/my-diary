package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.controller.request.NoteRequest
import br.com.katet.mydiary.adapter.database.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class UpdateNoteService(private val repository: DiaryRepository) {

    suspend fun execute(userId: Int, date: LocalDate, noteRequest: NoteRequest): String {

        withContext(Dispatchers.IO) {
            repository.findByUserIdAndDate(userId = userId, date = date)
        }
            ?.apply { this.notes = noteRequest.notes }?.also { clientDiaryInfo ->
                withContext(Dispatchers.IO){
                    repository.save(clientDiaryInfo)
                }
            }

        return "Note updated!"
    }
}