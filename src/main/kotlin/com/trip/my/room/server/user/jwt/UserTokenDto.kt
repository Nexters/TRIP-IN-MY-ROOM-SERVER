package com.trip.my.room.server.user.jwt

import java.util.*


class UserTokenDto {
	
	open class UserInfo {
		lateinit var userId : UUID
		lateinit var social : String
		var name : String ?= null
	}
}