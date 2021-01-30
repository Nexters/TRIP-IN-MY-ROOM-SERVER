package com.trip.my.room.server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.FormHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*


@Configuration
@EnableSwagger2
class SpringConfiguration {
	
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
	
}
