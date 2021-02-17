package com.trip.my.room.server.place

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PlaceRepository : JpaRepository<PlaceEntity, UUID>, PlaceRepositoryCustom {

    fun findAllByNameAndLatitudeAndLongitude(name: String?, latitude: Double?, longitude: Double?): PlaceEntity?

}
