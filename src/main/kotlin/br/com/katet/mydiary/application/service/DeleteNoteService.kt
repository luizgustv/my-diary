package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.database.DiaryRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DeleteNoteService(private val repository: DiaryRepository) {

    fun execute(userId: Int, date: LocalDate): String{
        repository.deleteByUserIdAndDate(userId = userId, date = date)
        return "Deleted!"
    }

    fun execute(): String{
        repository.deleteAll()
        return "All notes was deleted"
    }
}