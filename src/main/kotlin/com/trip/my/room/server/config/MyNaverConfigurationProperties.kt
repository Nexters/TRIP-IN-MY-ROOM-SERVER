package com.trip.my.room.server.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("my.propereties.naver")
class MyNaverConfigurationProperties {
	
	lateinit var responseType: String
	lateinit var clientId: String
	lateinit var secretKey: String
	lateinit var authBaseUrl: String
	lateinit var tokenApiUrl: String
	lateinit var userExtraInfoUrl: String
	lateinit var redirectUrl: String
	lateinit var grantType: String
	
	override fun toString(): String {
		return "MyNaverConfigurationProperties(responseType='$responseType', clientId='$clientId', " +
				"secretKey='$secretKey', authBaseUrl='$authBaseUrl', tokenApiUrl='$tokenApiUrl', " +
				"userExtraInfoUrl='$userExtraInfoUrl', redirectUrl='$redirectUrl')"
	}
	
	
}