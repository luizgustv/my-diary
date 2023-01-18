package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.database.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

private val log = LoggerFactory.getLogger(GetAllUsersService::class.java)

@Service
class GetAllUsersService(private val repository: DiaryRepository) {

    suspend fun execute(): List<Int>{
        log.info("Starting to get users in database...")

        return withContext(Dispatchers.IO){
            repository.findAll().map { note ->
                note.userId
            }
        }
    }
}