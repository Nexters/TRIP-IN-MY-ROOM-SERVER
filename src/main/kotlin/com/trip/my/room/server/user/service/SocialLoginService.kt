package com.trip.my.room.server.user.service

import com.trip.my.room.server.user.UserEntity
import com.trip.my.room.server.user.dto.UserDto
import com.trip.my.room.server.user.enum.SocialType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.security.InvalidParameterException

@Service
class SocialLoginService(@Autowired private val kakaoClientService: KakaoClientService) {
	
	fun getSocialUser(socialType: String, authorize_code: String): UserDto.UserJoinIn {
		if (SocialType.valueOf(socialType.toUpperCase()).equals(SocialType.KAKAO)) {
			return kakaoClientService.getUser(authorize_code)!!
		}
		throw InvalidParameterException("유효하지 않은 socialType 입니다. : ${socialType}")
	}
	
	fun getSocialUserWithToken(socialType: String, tokenType: String, token: String): UserDto.UserJoinIn {
		if (SocialType.valueOf(socialType.toUpperCase()).equals(SocialType.KAKAO)) {
			return kakaoClientService.getUserExtraInfo(tokenType, token)
		}
		throw InvalidParameterException("유효하지 않은 socialType 입니다. : ${socialType}")
	}
	
	fun unlinkWithSocial(socialType: String, user: UserEntity): Boolean {
		if (SocialType.valueOf(socialType.toUpperCase()).equals(SocialType.KAKAO)) {
			return kakaoClientService.unlinkWithKakao(user.socialId!!)
		}
		// 네이버 추가
		return false
	}
}
