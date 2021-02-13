package com.trip.my.room.server.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.FormHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Configuration
@EnableSwagger2
class SpringConfiguration {
	
	@PersistenceContext
	private val entityManager: EntityManager? = null
	
	@Bean
	fun restTemplate(): RestTemplate {
		var restTemplate = RestTemplate()
		restTemplate.messageConverters.add(FormHttpMessageConverter())
		restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())
		return restTemplate
	}
	
	@Bean
	fun setTimeZone(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
	}
	
	@Bean
	fun jpaQueryFactory(): JPAQueryFactory? {
		return JPAQueryFactory(entityManager)
	}
}
