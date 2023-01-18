package br.com.katet.mydiary.adapter.database

import br.com.katet.mydiary.adapter.controller.response.NoteResponse
import br.com.katet.mydiary.adapter.controller.response.NotesResponse
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "diary")
class Note(
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    val id: String = UUID.randomUUID().toString(),
    @Column(nullable = false)
    val userId: Int = 0,
    @Column(nullable = false)
    var timestamp: LocalDateTime = LocalDateTime.now(),
    @Column(nullable = false)
    var note: String = ""
    ){
}

fun Note.toResponse(): NoteResponse =
    NoteResponse(this.id, this.timestamp, this.note)

fun List<Note>.toResponse(): List<NotesResponse.Note> =
    this.map {
        NotesResponse.Note(id = it.id, timestamp = it.timestamp, note = it.note)
    }