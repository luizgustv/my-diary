package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.database.ClientDiaryInfo
import br.com.katet.mydiary.adapter.database.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

private val log = LoggerFactory.getLogger(GetNoteService::class.java)

@Service
class GetNoteService(private val repository: DiaryRepository) {

    suspend fun execute(userId: Int, date: LocalDate): ClientDiaryInfo? {

        log.info("Starting to get note in database... ")

        return withContext(Dispatchers.IO){
            repository.findByUserIdAndDate(userId = userId, date = date)
        }
    }
}