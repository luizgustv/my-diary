package br.com.katet.mydiary.adapter.database

import br.com.katet.mydiary.adapter.controller.response.ClientDiaryInfoResponse
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "diary")
class ClientDiaryInfo(
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private val id: String = UUID.randomUUID().toString(),
    @Column(nullable = false)
    val userId: Int = 0,
    @Column(nullable = false)
    private val date: LocalDate = LocalDate.now(),
    @Column(nullable = false)
    private val time: LocalTime = LocalTime.now(),
    @Column(nullable = false)
    var notes: String = ""
    ){

    fun toResponse(): ClientDiaryInfoResponse =
        ClientDiaryInfoResponse(this.id, this.userId, this.date, this.time, this.notes)
}