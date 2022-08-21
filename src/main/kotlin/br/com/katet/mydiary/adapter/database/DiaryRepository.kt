package br.com.katet.mydiary.adapter.database

import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface DiaryRepository: CrudRepository<ClientDiaryInfo, String> {

    fun findByUserIdAndDate(userId: Int, date: LocalDate): ClientDiaryInfo?

    fun deleteByUserIdAndDate(userId: Int, date: LocalDate)
}