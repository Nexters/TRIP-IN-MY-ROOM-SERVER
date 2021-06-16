package com.trip.my.room.server.place

import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class PlaceService(
    private val placeRepository: PlaceRepository,
    private val placeMapper: PlaceMapper
) {

    fun createCustomPlace(userId: UUID, placeIn: PlaceDto.PlaceIn): PlaceDto.PlaceOut {
        val newPlace = PlaceEntity().apply {
            this.name = placeIn.name
            this.latitude = placeIn.latitude
            this.longitude = placeIn.longitude
        }
        placeRepository.save(newPlace)
        return placeMapper.toDto(newPlace)
    }

    fun getPlaceDtoById(placeId: UUID): PlaceDto.PlaceOut {
        val placeEntity = placeRepository.findById(placeId)
            .orElseThrow { throw NoSuchElementException("해당 하는 place 정보가 없습니다.") }
        return placeMapper.toDto(placeEntity)
    }

    @Transactional
    fun getPlaceEntityByPlaceInDto(placeInDto: PlaceDto.PlaceIn): PlaceEntity {
        var foundPlaceEntity = placeRepository.findAllByNameAndLatitudeAndLongitude(
            placeInDto.name,
            placeInDto.latitude,
            placeInDto.longitude
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
    else placeRepository.findById(placeId).orElseThrow { throw NoSuchElementException("해당 하는 place 정보가 없습니다.") }

    private fun convertEntity(placeInDto: PlaceDto.PlaceIn): PlaceEntity {
        return PlaceEntity().apply {
            this.name = placeInDto.name
            this.latitude = placeInDto.latitude
            this.longitude = placeInDto.longitude
        }
    }

}
