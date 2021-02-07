package com.trip.my.room.server.user.exception

import org.springframework.http.HttpStatus

data class IfException(
		var errorCode: IfErrorCode = IfErrorCode.INTERNAL_SERVER_ERROR,
		var errorMessage: String = "",
		var httpStatus: HttpStatus = HttpStatus.BAD_REQUEST

) : RuntimeException() {
	
	constructor(errorCode: IfErrorCode) : this() {
		this.errorCode = errorCode
		this.errorMessage = errorCode.message
	}
	
	constructor(errorCode: IfErrorCode, httpStatus: HttpStatus) : this() {
		this.errorCode = errorCode
		this.errorMessage = errorCode.message
		this.httpStatus = httpStatus
	}
}