package com.trip.my.room.server.user.jwt

import com.trip.my.room.server.user.IfUserPrincipal
import com.trip.my.room.server.user.UserEntity
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.servlet.http.HttpServletRequest


@Service
class IfUserTokenService {
	
	// 토큰의 서명을 위한 값
	var ifSignKey : String = "IfIfIf";
	
	// https://do-study.tistory.com/106
	fun parseTokenString(request: HttpServletRequest): String? {
		val bearerToken = request.getHeader("Authorization")
		return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			bearerToken.substring(7)
		} else null
	}
	
	fun createToken(userEntity: UserEntity, expSec: Long = 3000): MutableMap<String, Any> {
		
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
		payloads.put("ID", userEntity.id!!) // User ID
		payloads.put("SOCIAL", userEntity.social!!) // Social 가입 경로
		
		var accessToken: String = Jwts.builder()
				.setHeader(headers)
				.setClaims(payloads)
				.signWith(SignatureAlgorithm.HS256, ifSignKey.toByteArray())
				.compact()
		
		return mutableMapOf<String, Any>("tokenType" to "Bearer", "access_token" to accessToken, "exp" to expriedDateTime)
	}
	
	fun getUserIdFromToken(token: String?): UUID {
		val claims: Claims = Jwts.parser()
				.setSigningKey(ifSignKey.toByteArray())
				.parseClaimsJws(token)
				.getBody()
		return UUID.fromString(claims.get("ID") as String)
	}
	
	fun verifyToken(token: String?): Boolean {
		try {
			var claims : Claims = Jwts.parser()
					.setSigningKey(ifSignKey.toByteArray())
					.parseClaimsJws(token)
					.body
		
			var expiredTime : Long = claims.get("EXP") as Long
			val currentTime : Long = Instant.now().toEpochMilli()
			if (currentTime < expiredTime){
				return true
			}
		} catch (e : Exception){
			return false
		}
		return false
	}
	
	fun createAuthentication(token: String, userId: UUID): IfAuthenticationToken {
		var ifUserPrincipal = IfUserPrincipal(userId)
		return IfAuthenticationToken(ifUserPrincipal, token)
	}
}

