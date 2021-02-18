package com.trip.my.room.server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.server.WebSession
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.OperationsSorter
import springfox.documentation.swagger.web.UiConfiguration
import springfox.documentation.swagger.web.UiConfigurationBuilder
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@EnableSwagger2
@Configuration
class SwaggerConfiguration {
	
	companion object {
		private const val AUTHORIZATION_HEADER = "Authorization"
		private const val DEFAULT_INCLUDE_PATTERN = "/.*"
		private const val SECURITY_URL_PATTERN = "/(users|stories|place|countries|pictures|).*"
	}
	
	@Bean
	fun swaggerApi(): Docket {
		return Docket(DocumentationType.SWAGGER_2)
				.enable(true)
				.apiInfo(getSwaggerServerInfo())
//				.useDefaultResponseMessages(true) // 기본 응답 리스트 생성
				.ignoredParameterTypes(
						WebSession::class.java,
						HttpRequest::class.java,
				)
				.genericModelSubstitutes(
						Optional::class.java,
						ResponseEntity::class.java
				)
				.securityContexts(arrayListOf(securityContext()))
				.securitySchemes(arrayListOf(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.trip.my.room.server"))
				.paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
				.build()
	}
	
	private fun getSwaggerServerInfo() = ApiInfoBuilder()
			.title("if-traveler API")
			.description("Nexters if-traveler.site REST API")
//            .contact(Contact("if", "if-travler.site", "histuckyi@gmail.com"))
			.license("Nexters if-traveler team")
			.build()
	
	@Bean
	fun uiConfiguration(): UiConfiguration? {
		return UiConfigurationBuilder.builder()
				.operationsSorter(OperationsSorter.METHOD)
				.build()
	}
	
	
	private fun apiKey() = ApiKey("Bearer +AccessToken", AUTHORIZATION_HEADER, "header")
	
	private fun securityContext() = SecurityContext.builder()
			.securityReferences(defaultAuth())
			.forPaths(PathSelectors.regex(SECURITY_URL_PATTERN))
			.build()
	
	private fun defaultAuth(): List<SecurityReference> =
			arrayListOf(SecurityReference("Bearer +AccessToken",
					arrayOf(AuthorizationScope("global", "accessEverything"))))
	
	
}
