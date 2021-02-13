package com.trip.my.room.server.place

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PlaceService(@Autowired private val placeRepository: PlaceRepository) {
	
	fun getPlaces(): MutableList<PlaceEntity> {
		return placeRepository.findAll()
	}
}