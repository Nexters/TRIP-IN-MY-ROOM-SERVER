package com.trip.my.room.server.place

interface PlaceRepositoryCustom {

    fun findByPlaceName(placeStr: String): PlaceEntity?

}
