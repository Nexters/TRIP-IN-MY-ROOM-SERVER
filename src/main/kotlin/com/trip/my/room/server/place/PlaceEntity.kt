package com.trip.my.room.server.place

import com.trip.my.room.server.country.CountryEntity
import java.util.*
import javax.persistence.*

@Entity(name = "place")
class PlaceEntity {
	
	@field: Id
	@field: GeneratedValue(strategy = GenerationType.AUTO)
	var id : UUID ?= null
	
	var name: String ?= null
	
	@field:ManyToOne(fetch = FetchType.LAZY)
	@field:JoinColumn(name="country_id")
	var country : CountryEntity ?= null
	
	// Rel 데이터가 사라지더라도 Place는 삭제되면 안됨
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
	var relList: MutableList<RelUserPlaceEntity> ?= mutableListOf()
}