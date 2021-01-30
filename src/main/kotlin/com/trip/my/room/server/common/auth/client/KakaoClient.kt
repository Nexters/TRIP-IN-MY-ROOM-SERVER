package com.trip.my.room.server.common.auth.client

import com.google.gson.Gson
import com.trip.my.room.server.common.auth.data.KakaoUser
import com.trip.my.room.server.config.MyConfigurationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.net.URI


@Service
class KakaoClient(@Autowired private val restTemplate: RestTemplate,
				  @Autowired private val myConfigProps: MyConfigurationProperties) {
	
	// autorize_code -> access_token, refresh_token
	fun getUser(authorize_code: String): KakaoUser? {
		var myMap = mapOf("Content-type" to listOf("application/x-www-form-urlencoded;charset=utf-8"))
		var values : MultiValueMap<String, String> = CollectionUtils.toMultiValueMap(myMap)
		val headers = HttpHeaders(values)
		
		var query = "?grant_type=${myConfigProps.grantType}"
		query += "&client_id=${myConfigProps.clientId}"
		query += "&redirect_uri=${myConfigProps.redirectUrl}"
		query += "&code=$authorize_code"
		
		val url: URI = URI.create(myConfigProps.authBaseUrl+query)
		val req = RequestEntity({}, headers, HttpMethod.POST, url)
		val re = restTemplate.exchange(req, String::class.java)
		return Gson().fromJson(re.body, KakaoUser::class.java)
	}
	
	// Todo 유저의 이메일 정보 가져오기
	fun getUserExtraInfo(){
	}
}