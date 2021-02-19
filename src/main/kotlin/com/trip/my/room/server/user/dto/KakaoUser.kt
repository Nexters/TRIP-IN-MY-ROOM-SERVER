package com.trip.my.room.server.user.dto

data class KakaoUser(
		var token_type : String,
		var access_token : String,
		var expires_in : Long, // 엑세스 토큰 만료 시간
		var refresh_token : String,
		var refresh_token_expires_in : Long, // 리프레시 토큰 만료 시간
		var scope : String
){}

data class KakaoUnlinkUser(
		var id : Int
){}