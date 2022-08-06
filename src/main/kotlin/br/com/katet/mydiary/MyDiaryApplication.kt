package br.com.katet.mydiary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyDiaryApplication

fun main(args: Array<String>) {
	runApplication<MyDiaryApplication>(*args)
}
