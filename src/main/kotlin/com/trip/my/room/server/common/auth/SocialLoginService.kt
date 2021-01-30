package com.trip.my.room.server.common.auth

import com.trip.my.room.server.common.auth.client.KakaoClient
import com.trip.my.room.server.common.auth.data.KakaoUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SocialLoginService(@Autowired private val kakaoClient: KakaoClient) {
	
	// 일단은 kakao만 사용
	fun getKakaoUser(socialType: String, authorize_code: String): KakaoUser? {
		return kakaoClient.getUser(authorize_code)
	}
}