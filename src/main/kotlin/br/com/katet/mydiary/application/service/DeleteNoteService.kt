package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.database.DiaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.coroutines.CoroutineContext

@Service
class DeleteNoteService(private val repository: DiaryRepository) {

    suspend fun execute(userId: Int, date: LocalDate): String{
        CoroutineScope(Dispatchers.IO).launch { repository.deleteByUserIdAndDate(userId = userId, date = date) }
        return "Deleted!"
    }

    suspend fun execute(): String{
        CoroutineScope(Dispatchers.IO).launch{ repository.deleteAll() }
        return "All notes was deleted"
    }
}