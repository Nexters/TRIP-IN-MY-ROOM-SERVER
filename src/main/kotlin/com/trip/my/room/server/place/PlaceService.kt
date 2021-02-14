package com.trip.my.room.server.place

import com.trip.my.room.server.user.UserEntity
import com.trip.my.room.server.user.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PlaceService(
    private val placeRepository: PlaceRepository,
    private val userRepository: UserRepository,
    private val placeMapper: PlaceMapper
) {

    fun getPlaces(userId: UUID): List<PlaceDto.PlaceOut> {
//        val placeResult = placeRepository.findAllByUserId(userId)
//        return placeMapper.toDtoList(placeResult!!)
        return emptyList()
    }

    fun searchPlaceByPlaceName(placeStr: String): List<PlaceDto.PlaceOut> {
        val placeResult = placeRepository.findByPlaceName(placeStr)
//        return placeMapper.toDtoList(placeResult!!)
        return emptyList()
    }

    fun createCustomPlace(userId: UUID, placeIn: PlaceDto.PlaceIn): PlaceDto.PlaceOut {
        val user: UserEntity = userRepository.findById(userId).get()
        val newPlace = PlaceEntity().apply {
            this.name = placeIn.name
            this.latitude = placeIn.latitude
            this.longtitude = placeIn.longtitude
        }
        placeRepository.save(newPlace)
        return placeMapper.toDto(newPlace)
    }

}
