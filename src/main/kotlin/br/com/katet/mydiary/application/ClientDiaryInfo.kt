package br.com.katet.mydiary.application

import org.hibernate.id.UUIDGenerator
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "diary")
class ClientDiaryInfo(
    @Id
    @GeneratedValue(generator = UUIDGenerator.GENERATOR_NAME, strategy = GenerationType.AUTO)
    private val id: String = UUID.randomUUID().toString(),
    @Column
    private val userId: Long = 0,
    @Column
    private val date: LocalDate = LocalDate.now(),
    @Column
    private val time: LocalTime = LocalTime.now(),
    @Column
    private val notes: String = ""
    ) {
}