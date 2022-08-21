package br.com.katet.mydiary.application.service

import br.com.katet.mydiary.adapter.controller.request.NoteRequest
import br.com.katet.mydiary.adapter.database.ClientDiaryInfo
import br.com.katet.mydiary.adapter.database.DiaryRepository
import kotlinx.coroutines.*
import org.springframework.stereotype.Service

@Service
class PostNoteService(private val repository: DiaryRepository) {

    suspend fun execute(noteRequest: NoteRequest): String {

        CoroutineScope(Dispatchers.IO).launch(CoroutineName("post-database")) {
            println("Start to save in database... Coroutine = ${coroutineContext[CoroutineName.Key]}, Thread = ${Thread.currentThread()}")
            repository.save(
                ClientDiaryInfo(userId = noteRequest.userId, notes = noteRequest.notes)
            )
            delay(2000)
            println("Done! Coroutine = ${coroutineContext[CoroutineName.Key]}, Thread = ${Thread.currentThread()}")
        }.join()

        return "Note salved!"
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
the function will send the string "Note salved" first and the operation still can be happening
 */