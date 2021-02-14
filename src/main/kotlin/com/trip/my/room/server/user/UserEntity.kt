package com.trip.my.room.server.user

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.*

@Entity(name = "user")
class UserEntity {
	
	@field: Id
	@field: GeneratedValue(generator = "uuid2", strategy = GenerationType.AUTO)
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@field: Column(columnDefinition = "BINARY(16)")
	var id: UUID ?= null
	
	@field:Column(nullable = true)
	var email: String ?= null
	
	var name: String ?= null
	
	// Redis에 저장하면 좋을 듯
	@field:Column(nullable = true)
	var refreshToken: String ?= null
	
	@field:Column(nullable = true)
	var social: String ?= null
	
	@field:Column(nullable = true)
	var socialId: String ?= null
	
}