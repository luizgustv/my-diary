package br.com.katet.mydiary.note

import org.springframework.data.repository.CrudRepository
import java.time.LocalDate
import java.util.*

interface DiaryRepository: CrudRepository<ClientDiaryInfo, String> {

    fun findByUserIdAndDate(userId: Int, date: LocalDate): ClientDiaryInfo

    fun deleteByUserIdAndDate(userId: Int, date: LocalDate)
}