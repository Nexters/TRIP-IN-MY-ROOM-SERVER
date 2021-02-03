package com.trip.my.room.server.user.dto


class UserResponseDto {
	
	open class BasicOut {
		val id: Long ?= null
		var name: String ?= null
		var email: String ?= null
		val social: String ?= null
	}
	
	open class BasicInfoOut{
		val type : String ?= null
		val token : String ?= null
		val exp : Long ?= null
	}
	
}
