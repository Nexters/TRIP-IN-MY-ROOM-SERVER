package com.trip.my.room.server.common.jwt


class UserTokenDto {
	
	open class UserInfo {
		lateinit var userId : String
		lateinit var social : String
		var name : String ?= null
	}
}