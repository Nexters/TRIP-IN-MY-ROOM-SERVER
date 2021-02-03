package com.trip.my.room.server.user.jwt

import com.trip.my.room.server.user.IfUserPrincipal
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class IfAuthenticationToken : AbstractAuthenticationToken {
	
	private var principal: IfUserPrincipal
	private var credentials: Any
	
	constructor(principal: IfUserPrincipal, token: String) : super(null)  {
		this.principal = principal
		this.credentials = token
		this.isAuthenticated = true
	}
	
	constructor(principal: IfUserPrincipal, token: String, authorities: Collection<GrantedAuthority?>?) : super(authorities) {
		this.principal = principal
		this.credentials = token
		this.isAuthenticated = true
	}
	
	override fun getCredentials(): Any? {
		return this.credentials
	}
	
	override fun getPrincipal(): Any? {
		return this.principal
	}
	
	companion object {
		private const val serialVersionUID = 46935234234234234L
	}
}