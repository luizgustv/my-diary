package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.controller.request.NoteRequest
import br.com.katet.mydiary.adapter.database.DiaryRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class UpdateNoteService(private val repository: DiaryRepository) {

    fun execute(userId: Int, date: LocalDate, noteRequest: NoteRequest): String{
        var clientDiaryInfo = repository.findByUserIdAndDate(userId = userId, date = date)

        clientDiaryInfo?.apply {this.notes = noteRequest.notes}?.also { clientDiaryInfo ->
            repository.save(clientDiaryInfo)
        }

        return "Note updated!"
    }
}