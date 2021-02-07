package com.trip.my.room.server.user.enum

enum class SocialType(private val value: Int) {
	KAKAO(100), NAVER(200);
	
	fun getValue() = value
}