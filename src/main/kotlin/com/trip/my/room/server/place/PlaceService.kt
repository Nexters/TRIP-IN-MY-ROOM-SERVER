package com.trip.my.room.server.place

import com.trip.my.room.server.user.UserEntity
import com.trip.my.room.server.user.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

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
            this.longitude = placeIn.longtitude
        }
        placeRepository.save(newPlace)
        return placeMapper.toDto(newPlace)
    }

    fun getPlaceDtoById(placeId: UUID): PlaceDto.PlaceOut {
        return placeMapper.toDto(placeRepository.findById(placeId).get())
    }

    @Transactional
    fun getPlaceEntityByPlaceInDto(placeInDto: PlaceDto.PlaceIn): PlaceEntity {
        var foundPlaceEntity = placeRepository.findAllByNameAndLatitudeAndLongitude(
            placeInDto.name,
            placeInDto.latitude,
            placeInDto.longtitude
        )

        if (foundPlaceEntity == null) {
            foundPlaceEntity = placeRepository.save(convertEntity(placeInDto))
        }

        return foundPlaceEntity
    }

    private fun convertEntity(placeInDto: PlaceDto.PlaceIn): PlaceEntity {
        return PlaceEntity().apply {
            this.name = placeInDto.name
            this.latitude = placeInDto.latitude
            this.longitude = placeInDto.longtitude
        }
    }

}
