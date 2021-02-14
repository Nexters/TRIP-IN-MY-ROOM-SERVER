package com.trip.my.room.server.place

import com.trip.my.room.server.country.CountryEntity
import com.trip.my.room.server.user.UserEntity
import java.util.*
import javax.persistence.*

@Entity(name = "place")
class PlaceEntity {
	
	@field: Id
	@field: GeneratedValue(strategy = GenerationType.AUTO)
	var id : UUID ?= null
	
	var name: String ?= null
	
	@field:ManyToOne(fetch = FetchType.LAZY)
	@field:JoinColumn(name="country_id", nullable = true)
	var country : CountryEntity ?= null
	
	// true -> user가 반드시 있어야 함
	var customized : Boolean ?= false
	
	@field: OneToOne
	@field: JoinColumn(name="user_id", nullable = true)
	var user : UserEntity ?= null
	
}