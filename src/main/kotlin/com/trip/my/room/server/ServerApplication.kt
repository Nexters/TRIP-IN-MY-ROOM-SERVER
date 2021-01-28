package com.trip.my.room.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@EnableJpaAuditing
@SpringBootApplication
class ServerApplication

fun main(args: Array<String>) {
	
	@Bean
	fun setTimeZone(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
	}
	
	runApplication<ServerApplication>(*args)
}
