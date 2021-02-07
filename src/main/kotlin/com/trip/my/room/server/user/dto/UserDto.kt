package com.trip.my.room.server.user.dto

class UserDto {
	
	open class UserJoinIn {
		var email: String ?= null
		var name: String ?= null
		//		val refreshToken: String ?= null
		var social: String ?= null
		var socialId: String ?= null
	}
	
	open class BasicOut {
		val id: Long ?= null
		var name: String ?= null
		var email: String ?= null
		val social: String ?= null
	}
	
	open class BasicJoInOut{
		var tokenType : String ?= null
		var accessToken : String ?= null
		var refreshToken : String ?= null
		var exp : Long ?= null
	}
	
	open class BasicInfoOut {
		var email : String ?= null
		var name : String ?= null
		var social : String ?= null
	}
}