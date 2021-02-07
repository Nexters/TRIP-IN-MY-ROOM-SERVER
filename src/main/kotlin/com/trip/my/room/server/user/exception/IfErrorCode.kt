package com.trip.my.room.server.user.exception


enum class IfErrorCode(val message: String) {
	
	// 응답 오류 코드에 대한 정의
	INTERNAL_SERVER_ERROR("알 수 없는 오류가 발생했습니다. 관계자에게 문의하세요."),
	UNSUPPORTED_HTTP_METHOD("지원하지 않는 HTTP 메써드입니다."),
	INVALID_HTTP_MESSAGE_BODY("HTTP 요청 바디의 형식이 잘못되었습니다."),
	EXPIRED_REFRESH_TOKEN("이미 만료된 리프레시 토큰입니다"),
	MATCH_BETWEEN_TOKENS("리프레시 토큰와 엑세스 토큰의 사용자가 일치하지 않습니다")
}