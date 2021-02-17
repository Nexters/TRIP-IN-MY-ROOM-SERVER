package com.trip.my.room.server.user.jwt.filter

import com.trip.my.room.server.user.jwt.IfUserTokenService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class IfTokenAuthenticationFilter() : OncePerRequestFilter() {
	
	// 에러 주의
	private val ifUserTokenService: IfUserTokenService = IfUserTokenService()
	
	override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
		val token = ifUserTokenService.parseTokenString(request)
		var authenticated: Boolean = true
		if (ifUserTokenService.verifyToken(token)) {
//		if (("/users/token/refresh" in request.requestURI) or ifUserTokenService.verifyToken(token)){
			val userId = ifUserTokenService.getUserIdFromToken(token)
			try {
				val authentication = ifUserTokenService.createAuthentication(token!!, userId, authenticated)
				authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
				SecurityContextHolder.getContext().authentication = authentication
			} catch (e: UsernameNotFoundException) {
				println("유효하지 않은 인증토큰입니다. 인증토큰 회원 오류")
			}
		}
		// 필터 체인에 등록
		filterChain.doFilter(request, response);
	}
	
}