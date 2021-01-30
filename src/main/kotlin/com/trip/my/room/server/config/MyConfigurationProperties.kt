package com.trip.my.room.server.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("my.propereties.kakao")
class MyConfigurationProperties {
	
	lateinit var clientId: String
	lateinit var authBaseUrl : String
	lateinit var apiBaseUrl : String
	lateinit var redirectUrl : String
	lateinit var grantType : String
	
	override fun toString(): String {
		return "MyConfigurationProperties(clientId='$clientId', authBaseUrl='$authBaseUrl'," +
				" apiBaseUrl='$apiBaseUrl', redirectUrl='$redirectUrl', grantType='$grantType')"
	}
	
	
}