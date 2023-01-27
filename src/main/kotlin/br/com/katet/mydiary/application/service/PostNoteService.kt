package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.database.Note
import br.com.katet.mydiary.adapter.database.DiaryRepository
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

private val log = LoggerFactory.getLogger(PostNoteService::class.java)

@Service
class PostNoteService(private val repository: DiaryRepository) {

    suspend fun execute(userId: Int, note: String): String {

        try {
            CoroutineScope(Dispatchers.IO).launch(CoroutineName("post-database")) {
                log.info("Starting to save in database... ")

                repository.save(
                    Note(userId = userId, note = note)
                )
                //delay(2000)
            }.join()
        }catch (ex: Exception){
            return "Error"
        }
        return "Note saved in database"
    }
}