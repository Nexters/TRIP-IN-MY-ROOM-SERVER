package com.trip.my.room.server.user.exception

import com.fasterxml.jackson.annotation.JsonProperty

// 클라이언트에게 예외를 출력할 응답 클래스
data class IfErrorResponse(
		
		// 사전 정의된 오류 코드
		@JsonProperty
		val errorCode: String,
		
		// 해당 오류 코드에 대한 설명
		@JsonProperty
		val errorMessage: String
)