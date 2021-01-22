package com.trip.my.room.server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.OperationsSorter
import springfox.documentation.swagger.web.UiConfiguration
import springfox.documentation.swagger.web.UiConfigurationBuilder

@Configuration
class SwaggerConfiguration {

    @Bean
    fun swaggerApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .build()
            .apiInfo(this.getSwaggerServerInfo())
    }

    private fun getSwaggerServerInfo(): ApiInfo? {
        return ApiInfoBuilder()
            .title("Trip in my room")
            .version("0.0.1")
            .build()
    }

    @Bean
    fun uiConfiguration(): UiConfiguration? {
        return UiConfigurationBuilder.builder()
            .operationsSorter(OperationsSorter.METHOD)
            .build()
    }

}
