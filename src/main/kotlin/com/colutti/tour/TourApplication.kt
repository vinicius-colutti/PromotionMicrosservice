package com.colutti.tour

import com.colutti.tour.model.Promocao
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.concurrent.ConcurrentHashMap

@SpringBootApplication
class TourApplication

fun main(args: Array<String>) {
	runApplication<TourApplication>(*args)
}

