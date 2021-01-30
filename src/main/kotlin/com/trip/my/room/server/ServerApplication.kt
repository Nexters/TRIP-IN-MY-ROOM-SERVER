package com.trip.my.room.server

import com.trip.my.room.server.config.MyConfigurationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(MyConfigurationProperties::class)
class ServerApplication

fun main(args: Array<String>) {
	runApplication<ServerApplication>(*args)
}
