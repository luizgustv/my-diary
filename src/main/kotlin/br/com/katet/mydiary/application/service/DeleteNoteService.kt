package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.database.DiaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

private val log = LoggerFactory.getLogger(DeleteNoteService::class.java)

@Service
class DeleteNoteService(private val repository: DiaryRepository) {
    suspend fun execute(): String{
        log.info("Starting to delete all notes in database... ")

        CoroutineScope(Dispatchers.IO).launch{ repository.deleteAll() }
        return "All notes was deleted"
    }

    @Transactional
    suspend fun execute(userId: Int, id: String): String{
        log.info("Starting to delete note in database... ")

        repository.deleteByUserIdAndId(userId = userId, id = id)

        return "Deleted!"
    }

    /*
    If I send the method delete() to another thread I receive error, because the other thread don't have permission to operate transactionals operations

    https://stackoverflow.com/questions/64136645/is-it-possible-to-use-transactional-and-kotlin-coroutines-in-spring-boot

    Interactions between @Transactional and Coroutines
     */
}