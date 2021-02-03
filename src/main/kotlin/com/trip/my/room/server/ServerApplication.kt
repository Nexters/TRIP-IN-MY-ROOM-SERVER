package com.trip.my.room.server

import com.trip.my.room.server.config.MyConfigurationProperties
import com.trip.my.room.server.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(MyConfigurationProperties::class)
class ServerApplication {
	
	
	@Autowired
	private val userService: UserService? = null
	
	
	@EventListener(ApplicationReadyEvent::class)
	fun afterStartUp() {
	}
	
	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
//			System.setProperty("spring.devtools.restart.enabled", "false");
			runApplication<ServerApplication>(*args)
		}
	}
}


