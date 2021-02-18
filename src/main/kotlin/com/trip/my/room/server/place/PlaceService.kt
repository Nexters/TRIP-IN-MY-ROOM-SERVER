package com.trip.my.room.server.place

import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class PlaceService(
    private val placeRepository: PlaceRepository,
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

    fun findPlaceEntityIfExistIdOrNotMakeNewEntity(
        placeId: UUID?,
        placeInDto: PlaceDto.PlaceIn
    ) = if (placeId == null) placeRepository.save(convertEntity(placeInDto))
    else placeRepository.findById(placeId).get()

    private fun convertEntity(placeInDto: PlaceDto.PlaceIn): PlaceEntity {
        return PlaceEntity().apply {
            this.name = placeInDto.name
            this.latitude = placeInDto.latitude
            this.longitude = placeInDto.longtitude
        }
    }

}
