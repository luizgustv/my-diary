package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.database.ClientDiaryInfo
import br.com.katet.mydiary.adapter.database.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.log4j.Logger
import org.springframework.stereotype.Service
import java.time.LocalDate

private val log: Logger = Logger.getLogger("GetNoteService")

@Service
class GetNoteService(private val repository: DiaryRepository) {

    suspend fun execute(userId: Int, date: LocalDate): ClientDiaryInfo? {

        log.info("Starting to get note in database... ")

        return withContext(Dispatchers.IO){
            repository.findByUserIdAndDate(userId = userId, date = date)
        }
    }
}