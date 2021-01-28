package com.trip.my.room.server.common.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


@Service
class IfUserTokenService {
	
	// 토큰의 서명을 위한 값
	var ifSignKey : String = "IfIfIf";
	
	fun createToken(userInfo: UserTokenDto.UserInfo, expSec : Long = 60 ): String {
		
		// Headers
		var headers : MutableMap<String, Any> = mutableMapOf();
		headers.put("typ", "jwt")
		headers.put("alg", "HS256")
		
		// Payloads
		var payloads : MutableMap<String, Any> = mutableMapOf()
		var addExpSec: Long = expSec
		var iat = LocalDateTime.now()
		var expriedDateTime : Long = iat.plusSeconds(addExpSec).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
		
		payloads.put("AUD", "user") // 토큰 대상자
		payloads.put("SUB", "NextersIf") // 토큰 제목
		payloads.put("ISS", "Nexters6team") // 토큰 발급자
		payloads.put("EXP", expriedDateTime) // 토큰 만료일(Instant)
		payloads.put("IAT", iat.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) // 토큰이 발급된 시간(Instant)
		payloads.put("ID", userInfo.userId) // User ID
		payloads.put("SOCIAL", userInfo.social) // Social 가입 경로
		
		var jwt: String = Jwts.builder()
				.setHeader(headers)
				.setClaims(payloads)
				.signWith(SignatureAlgorithm.HS256, ifSignKey.toByteArray())
				.compact()
		
		return jwt
	}
	
	fun verifyToken(token : String): Boolean {
		var claims : Claims = Jwts.parser()
				.setSigningKey(ifSignKey.toByteArray())
				.parseClaimsJws(token)
				.body
		
		var expiredTime : Long = claims.get("EXP") as Long
		println(expiredTime)
		val currentTime : Long = Instant.now().toEpochMilli()
		if (currentTime < expiredTime){
			println("유효함")
			return true
		} else {
			println("만료됨")
		}
		return false
	}
	
}

