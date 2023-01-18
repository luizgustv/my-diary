package br.com.katet.mydiary.adapter.database

import org.springframework.data.repository.CrudRepository

interface DiaryRepository: CrudRepository<Note, String> {
    fun findByUserId(userId: Int): List<Note>
    fun findByUserIdAndId(userId: Int, id: String): Note?
    fun deleteByUserIdAndId(userId: Int, id: String)
}