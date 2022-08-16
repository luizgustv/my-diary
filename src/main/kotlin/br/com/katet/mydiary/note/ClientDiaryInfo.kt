package br.com.katet.mydiary.note

import org.hibernate.annotations.GenericGenerator
import org.hibernate.id.UUIDGenerator
import java.math.BigDecimal
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
    private val userId: Int = 0,
    @Column(nullable = false)
    private val date: LocalDate = LocalDate.now(),
    @Column(nullable = false)
    private val time: LocalTime = LocalTime.now(),
    @Column(nullable = false)
    private val notes: String = ""
    ){

    fun toResponse(): ClientDiaryInfoResponse =
        ClientDiaryInfoResponse(this.id, this.userId, this.date, this.time, this.notes)
}