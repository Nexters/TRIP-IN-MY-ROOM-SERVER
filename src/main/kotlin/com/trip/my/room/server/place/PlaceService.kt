package com.trip.my.room.server.place

import com.trip.my.room.server.country.CountryEntity
import com.trip.my.room.server.user.UserEntity
import com.trip.my.room.server.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PlaceService(@Autowired private val placeRepository: PlaceRepository,
				   @Autowired private val userRepository: UserRepository,
				   @Autowired private val placeMapper: PlaceMapper) {
	
	fun getPlaces(userId: UUID): List<PlaceDto.PlaceOut> {
		val placeResult = placeRepository.findAllByUserId(userId)
		return placeMapper.toDtoList(placeResult!!)
	}
	
	fun searchPlaceByPlaceName(placeStr: String): List<PlaceDto.PlaceOut> {
		val placeResult = placeRepository.searchPlaceByPlaceName(placeStr)
		return placeMapper.toDtoList(placeResult!!)
	}
	
	// 개념적으로는 Country이지만 구현상 Place가 추가된다.
	fun createCustomCountry(userId: UUID, country: CountryEntity, placeIn: PlaceDto.PlaceIn): PlaceDto.PlaceOut {
		val user: UserEntity = userRepository.findById(userId).get()
		val newPlace = PlaceEntity().apply {
			this.name = placeIn.name
			this.customized = placeIn.customized // true
			this.user = user
			this.country = country
		}
		placeRepository.save(newPlace)
		return placeMapper.toDto(newPlace)
	}
	
}