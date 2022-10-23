package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.database.DiaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.log4j.Logger
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.coroutines.CoroutineContext

private val log: Logger = Logger.getLogger("DeleteNoteService")

@Service
class DeleteNoteService(private val repository: DiaryRepository) {

    suspend fun execute(userId: Int, date: LocalDate): String{
        log.info("Starting to delete note in database... ")

        CoroutineScope(Dispatchers.IO).launch { repository.deleteByUserIdAndDate(userId = userId, date = date) }
        return "Deleted!"
    }

    suspend fun execute(): String{
        log.info("Starting to delete all notes in database... ")

        CoroutineScope(Dispatchers.IO).launch{ repository.deleteAll() }
        return "All notes was deleted"
    }
}