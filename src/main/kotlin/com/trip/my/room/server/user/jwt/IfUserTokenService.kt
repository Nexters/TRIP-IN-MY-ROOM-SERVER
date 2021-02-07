package com.trip.my.room.server.user.jwt

import com.trip.my.room.server.user.IfUserPrincipal
import com.trip.my.room.server.user.UserEntity
import com.trip.my.room.server.user.exception.IfErrorCode
import com.trip.my.room.server.user.exception.IfException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
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
	
	fun createToken(userEntity: UserEntity, expSec: Long = 300): MutableMap<String, Any> {
		
		// Headers
		var headers : MutableMap<String, Any> = mutableMapOf();
		headers.put("typ", "jwt")
		headers.put("alg", "HS256")
		
		// Payloads
		var payloads : MutableMap<String, Any> = mutableMapOf()
		var addExpSec: Long = expSec
		var iat = LocalDateTime.now()
		var expriedDateTime : Long = iat.plusSeconds(addExpSec).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
		
		payloads.put("aud", "user") // 토큰 대상자
		payloads.put("sub", "NextersIf") // 토큰 제목
		payloads.put("iss", "Nexters6team") // 토큰 발급자
		payloads.put("exp", expriedDateTime) // 토큰 만료일(Instant)
		payloads.put("iat", iat.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) // 토큰이 발급된 시간(Instant)
		payloads.put("id", userEntity.id!!) // User ID
		payloads.put("social", userEntity.social!!) // Social 가입 경로
		
		var accessToken: String = Jwts.builder()
				.setHeader(headers)
				.setClaims(payloads)
				.signWith(SignatureAlgorithm.HS256, ifSignKey.toByteArray())
				.compact()
		
		var refreshToken : String = createRefreshToken(userEntity)
		
		return mutableMapOf<String, Any>("tokenType" to "Bearer",
											"access_token" to accessToken,
											"refresh_token" to refreshToken,
											"exp" to expriedDateTime)
	}
	fun createRefreshToken(userEntity : UserEntity, refreshExpirationDateInMinutes: Long = 1440*3): String {
		// Headers
		var headers : MutableMap<String, Any> = mutableMapOf();
		headers.put("typ", "jwt")
		headers.put("alg", "HS256")
		
		// Payloads
		var payloads : MutableMap<String, Any> = mutableMapOf()
		var addExpMinutes: Long = refreshExpirationDateInMinutes // 3days(1440(1day) * 3)
		var iat = LocalDateTime.now()
		var expriedDateTime : Long = iat.plusMinutes(addExpMinutes).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
		
		payloads.put("aud", "user") // 토큰 대상자
		payloads.put("sub", "NextersIf") // 토큰 제목
		payloads.put("iss", "Nexters6team") // 토큰 발급자
		payloads.put("exp", expriedDateTime) // 토큰 만료일(Instant)
		payloads.put("iat", iat.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) // 토큰이 발급된 시간(Instant)
		payloads.put("id", userEntity.id!!) // User ID
		payloads.put("social", userEntity.social!!) // Social 가입 경로
		
		return Jwts.builder()
				.setHeader(headers)
				.setClaims(payloads)
				.signWith(SignatureAlgorithm.HS256, ifSignKey.toByteArray())
				.compact()
	}
	
	fun getUserIdFromToken(token: String?): UUID {
		val claims: Claims = Jwts.parser()
				.setSigningKey(ifSignKey.toByteArray())
				.parseClaimsJws(token)
				.getBody()
		return UUID.fromString(claims.get("id") as String)
	}
	
	fun verifyToken(token: String?): Boolean {
		try {
			var claims : Claims = Jwts.parser()
					.setSigningKey(ifSignKey.toByteArray())
					.parseClaimsJws(token)
					.body
		
			var expiredTime : Long = claims.get("exp") as Long
			val currentTime : Long = Instant.now().toEpochMilli()
			if (currentTime < expiredTime){
				return true
			}
		} catch (e : Exception){
			return false
		}
		return false
	}
	
	fun reNewAccessToken(userEntity: UserEntity, refreshToken: String, expSec:Long = 300): MutableMap<String, Any> {
		val isAvailable : Boolean =  verifyToken(refreshToken)
		if (isAvailable) {
			var userId = getUserIdFromToken(refreshToken)
			if (userEntity.id != userId) {
				throw IfException(errorCode = IfErrorCode.MATCH_BETWEEN_TOKENS,
						errorMessage = "리프레시 토큰과 엑세스 토큰의 사용자가 일치하지 않습니다",
						httpStatus = HttpStatus.BAD_REQUEST)
			}
			return createToken(userEntity, expSec) // expSec = 300 , 5minutes
		}
		throw IfException(errorCode = IfErrorCode.EXPIRED_REFRESH_TOKEN,
						errorMessage = "리프레시 토큰이 만료되어 엑세스 토큰을 갱신하지 못하였습니다.",
						httpStatus = HttpStatus.BAD_REQUEST)
	}
	
	fun createAuthentication(token: String, userId: UUID, authenticated: Boolean = true): IfAuthenticationToken {
		var ifUserPrincipal = IfUserPrincipal(userId)
		return IfAuthenticationToken(ifUserPrincipal, token, authenticated)
	}
}

