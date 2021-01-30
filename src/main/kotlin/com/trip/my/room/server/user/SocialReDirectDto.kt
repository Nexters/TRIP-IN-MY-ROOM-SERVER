package com.trip.my.room.server.user

class SocialReDirectDto {
	
	open class KakaoDto{
		var code : String ?= null
		var state : String ?= null
		var error : String ?= null
		var errorDescription : String ?= null
	}
}