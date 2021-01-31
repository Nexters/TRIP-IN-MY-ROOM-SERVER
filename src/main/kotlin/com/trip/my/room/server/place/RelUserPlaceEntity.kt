package com.trip.my.room.server.place

import com.trip.my.room.server.user.UserEntity
import java.util.*
import javax.persistence.*

@Entity(name = "rel_user_and_place")
class RelUserPlaceEntity {
	
	@field:Id
	@field:GeneratedValue(strategy = GenerationType.AUTO)
	var id : UUID ?= null
	
	@field:ManyToOne(fetch = FetchType.LAZY)
	@field:JoinColumn(name = "place_id")
	var place : PlaceEntity ?= null
	
	@field:ManyToOne(fetch = FetchType.LAZY)
	@field:JoinColumn(name = "user_id")
	var user: UserEntity ?= null
}
