package com.trip.my.room.server.user.service

import com.trip.my.room.server.user.service.KakaoClientService
import com.trip.my.room.server.user.dto.KakaoUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SocialLoginService(@Autowired private val kakaoClientService: KakaoClientService) {
	
	// 일단은 kakao만 사용
	fun getKakaoUser(socialType: String, authorize_code: String): KakaoUser {
		return kakaoClientService.getUser(authorize_code)!!
	}
}