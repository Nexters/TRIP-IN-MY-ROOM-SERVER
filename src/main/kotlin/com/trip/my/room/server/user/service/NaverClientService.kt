package com.trip.my.room.server.user.service

import com.google.gson.Gson
import com.trip.my.room.server.config.MyNaverConfigurationProperties
import com.trip.my.room.server.user.dto.NaverUnlinkUser
import com.trip.my.room.server.user.dto.NaverUser
import com.trip.my.room.server.user.dto.UserDto
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.net.URI

@Service
class NaverClientService(private val restTemplate: RestTemplate,
						 private val myNaverConfigProps: MyNaverConfigurationProperties) {
	
	// authorize_code -> get access_token, refresh_token from kakao
	fun getUser(authorize_code: String): UserDto.UserJoinIn {
		val headers = HttpHeaders()
		
		var query = "?grant_type=${myNaverConfigProps.grantType}"
		query += "&client_id=${myNaverConfigProps.clientId}"
		query += "&client_secret=${myNaverConfigProps.secretKey}"
		query += "&code=$authorize_code"
		query += "&state=test"
		
		val url: URI = URI.create(myNaverConfigProps.tokenApiUrl + query)
		val req = RequestEntity({}, headers, HttpMethod.POST, url)
		val re = restTemplate.exchange(req, String::class.java)
		val nUser = Gson().fromJson(re.body, NaverUser::class.java)
		return getUserExtraInfo(token_type = nUser.token_type, token = nUser.access_token)
	}
	
	fun getUserExtraInfo(token_type: String, token: String): UserDto.UserJoinIn {
		var myMap = mapOf("Content-type" to listOf("application/x-www-form-urlencoded;charset=utf-8"),
				"Authorization" to listOf("${token_type} ${token}"))
		var values: MultiValueMap<String, String> = CollectionUtils.toMultiValueMap(myMap)
		val headers = HttpHeaders(values)
		
		val url: URI = URI.create(myNaverConfigProps.userExtraInfoUrl)
		val req = RequestEntity({}, headers, HttpMethod.GET, url)
		val re = restTemplate.exchange(req, Any::class.java)
		val container = re.body as HashMap<String, Any>
		var userInfo = container["response"] as HashMap<String, Any>
		return UserDto.UserJoinIn().apply {
			this.name = userInfo["name"] as String?
			this.email = userInfo["email"] as String?
			this.socialId = userInfo["id"] as String?
			this.social = "naver"
		}
	}
	
	fun unlinkFromNaver(user_social_id: String, access_token: String): Boolean {
		val headers = HttpHeaders()
		
		var query = "?grant_type=delete"
		query += "&client_id=${myNaverConfigProps.clientId}"
		query += "&client_secret=${myNaverConfigProps.secretKey}"
		query += "&access_token=$access_token"
		query += "&service_provider=NAVER"
		query += "&state=test"
		
		val url: URI = URI.create(myNaverConfigProps.tokenApiUrl + query)
		val req = RequestEntity({}, headers, HttpMethod.POST, url)
		val re = restTemplate.exchange(req, String::class.java)
		var unlinkUser = Gson().fromJson(re.body, NaverUnlinkUser::class.java)
		if (unlinkUser.result == "success") {
			return true
		}
		return false
	}
	
}
