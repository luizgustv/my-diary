package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.database.ClientDiaryInfo
import br.com.katet.mydiary.adapter.database.DiaryRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class GetNoteService(private val repository: DiaryRepository) {

    fun execute(userId: Int, date: LocalDate): ClientDiaryInfo? {
        return repository.findByUserIdAndDate(userId = userId, date = date)
    }
}