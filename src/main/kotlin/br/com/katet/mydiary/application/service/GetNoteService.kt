package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.database.Note
import br.com.katet.mydiary.adapter.database.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

private val log = LoggerFactory.getLogger(GetNoteService::class.java)

@Service
class GetNoteService(private val repository: DiaryRepository) {

    suspend fun execute(userId: Int): List<Note> {
        log.info("Starting to get note in database... ")

        return withContext(Dispatchers.IO){
            repository.findByUserId(userId = userId)
        }
    }

    suspend fun execute(userId: Int, id: String): Note? {
        log.info("Starting to get note in database... ")

        return withContext(Dispatchers.IO){
            repository.findByUserIdAndId(userId = userId, id = id)
        }
    }
}