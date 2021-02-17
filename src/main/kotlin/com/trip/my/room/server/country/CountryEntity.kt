package com.trip.my.room.server.country

import com.trip.my.room.server.place.PlaceEntity
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.*

@Entity(name = "country")
class CountryEntity {
	
	@field:Id
	@field:GeneratedValue(strategy = GenerationType.AUTO)
	var id: UUID? = null
	
	var name: String? = null
	
	// S3 URL
	var countryIcon: String? = null
	
	var customized: Boolean? = null
	
	@field:OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
	@field:OnDelete(action = OnDeleteAction.CASCADE)
	var myPlaceList: MutableList<PlaceEntity>? = mutableListOf()
}