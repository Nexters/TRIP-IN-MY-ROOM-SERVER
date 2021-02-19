package com.trip.my.room.server.user.dto

data class NaverUser(
		var token_type: String,
		var access_token: String,
		var refresh_token: String,
		var expires_in: Long, // 접근 토큰의 유효 기간(초 단위)
		var error: String, // 리프레시 토큰 만료 시간
		var error_description: String
) {}

data class NaverUnlinkUser(
		var access_token: String,
		var result: String
) {}