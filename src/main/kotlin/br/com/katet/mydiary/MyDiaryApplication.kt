package br.com.katet.mydiary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
class MyDiaryApplication

fun main(args: Array<String>) {
	runApplication<MyDiaryApplication>(*args)
}
