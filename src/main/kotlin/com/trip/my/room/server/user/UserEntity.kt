package com.trip.my.room.server.user

import com.trip.my.room.server.place.RelUserPlaceEntity
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.persistence.*

@Entity(name = "user")
class UserEntity {
	
	@field: Id
	@field: GeneratedValue(strategy = GenerationType.AUTO)
	var id: UUID ?= null
	
	var email: String ?= null
	
	var name: String ?= null
	
	var social: String ?= null
	
	var socialId: String ?= null
	
	// User 삭제시 rel 테이블의 값도 자동 삭제
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@OnDelete(action = OnDeleteAction.CASCADE)
	var relList: MutableList<RelUserPlaceEntity> ?= mutableListOf()
}