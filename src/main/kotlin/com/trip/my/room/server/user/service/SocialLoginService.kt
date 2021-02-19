package com.trip.my.room.server.user.service

import com.trip.my.room.server.user.UserEntity
import com.trip.my.room.server.user.dto.UserDto
import com.trip.my.room.server.user.enum.SocialType
import org.springframework.stereotype.Service
import java.security.InvalidParameterException

@Service
class SocialLoginService(private val kakaoClientService: KakaoClientService,
						 private val naverClientService: NaverClientService) {
	
	fun getSocialUser(socialType: String, authorize_code: String): UserDto.UserJoinIn {
		if (SocialType.valueOf(socialType.toUpperCase()).equals(SocialType.KAKAO)) {
			return kakaoClientService.getUser(authorize_code)!!
		} else if (SocialType.valueOf(socialType.toUpperCase()).equals(SocialType.NAVER)) {
			return naverClientService.getUser(authorize_code)!!
		}
		throw InvalidParameterException("유효하지 않은 socialType 입니다. : ${socialType}")
	}
	
	fun getSocialUserWithToken(socialType: String, tokenType: String, token: String): UserDto.UserJoinIn {
		if (SocialType.valueOf(socialType.toUpperCase()).equals(SocialType.KAKAO)) {
			return kakaoClientService.getUserExtraInfo(tokenType, token)
		}
		if (SocialType.valueOf(socialType.toUpperCase()).equals(SocialType.NAVER)) {
			return naverClientService.getUserExtraInfo(tokenType, token)
		}
		throw InvalidParameterException("유효하지 않은 socialType 입니다. : ${socialType}")
	}
	
	fun unlinkWithSocial(socialType: String, user: UserEntity, access_token: String? = null): Boolean {
		if (SocialType.valueOf(socialType.toUpperCase()).equals(SocialType.KAKAO)) {
			return kakaoClientService.unlinkWithKakao(user.socialId!!)
		} else if (SocialType.valueOf(socialType.toUpperCase()).equals(SocialType.NAVER)) {
			// 네이버 추가
			if (access_token == null) {
				return false
			}
			return naverClientService.unlinkFromNaver(user.socialId!!, access_token)
			return false
		}
		return false
	}
}
