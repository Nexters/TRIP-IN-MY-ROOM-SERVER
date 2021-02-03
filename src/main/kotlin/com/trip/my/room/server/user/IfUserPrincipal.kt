package com.trip.my.room.server.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


class IfUserPrincipal : UserDetails {
	// 유저 정보를 로그인 시 SecurityContext에 저장
	// 저장한 유저 정보를 SecurityContextHolder로 조회
	private var userId: UUID
	
	constructor(userId: UUID){
		this.userId = userId
	}
	
	fun getUserUUID(): UUID {
		return userId
	}
	
	fun setUserUUID(userId: UUID) {
		this.userId = userId
	}
	
	override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
		return mutableListOf()
	}
	
	override fun getPassword(): String {
		return "password"
	}
	
	override fun getUsername(): String {
		return "username"
	}
	
	override fun isAccountNonExpired(): Boolean {
		return true
	}
	
	override fun isAccountNonLocked(): Boolean {
		return true
	}
	
	override fun isCredentialsNonExpired(): Boolean {
		return true
	}
	
	override fun isEnabled(): Boolean {
		return true
	}
}