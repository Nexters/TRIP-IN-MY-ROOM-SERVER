package com.trip.my.room.server.place

import java.util.*

interface PlaceRepositoryCustom {
	
	fun findAllByUserId(userId: UUID) : MutableList<PlaceEntity>?
	
	fun searchPlaceByPlaceName(placeStr: String): MutableList<PlaceEntity>?
}