package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.controller.request.NoteRequest
import br.com.katet.mydiary.adapter.database.ClientDiaryInfo
import br.com.katet.mydiary.adapter.database.DiaryRepository
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

private val log = LoggerFactory.getLogger(PostNoteService::class.java)

@Service
class PostNoteService(private val repository: DiaryRepository) {

    suspend fun execute(noteRequest: NoteRequest): String {
        CoroutineScope(Dispatchers.IO).launch(CoroutineName("post-database")) {
            //Coroutine = ${coroutineContext[CoroutineName.Key]}, Thread = ${Thread.currentThread().name}
            log.info("Starting to save in database... ")

            repository.save(
                ClientDiaryInfo(userId = noteRequest.userId, notes = noteRequest.notes)
            )
            delay(2000)
            log.info("Done")
        }.join()

        return "Note saved in database"
    }
}
/*
Tip:
        CoroutineScope(Dispatchers.IO).async {
            println("Start to save in database...")
            repository.save(
                ClientDiaryInfo(userId = noteRequest.userId, notes = noteRequest.notes)
            )
            delay(5000)
            println("Done!")
        }

        return "Note salved!"

With this configuration, if you don't call the join() or await() method in async,
the function will send the string "Note salved" first and the operation can still be happening
 */