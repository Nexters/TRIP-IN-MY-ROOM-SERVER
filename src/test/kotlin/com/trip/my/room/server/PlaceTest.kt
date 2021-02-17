package com.trip.my.room.server

import com.trip.my.room.server.country.CountryEntity
import com.trip.my.room.server.country.CountryRepository
import com.trip.my.room.server.place.PlaceEntity
import com.trip.my.room.server.place.PlaceMapper
import com.trip.my.room.server.place.PlaceRepository
import com.trip.my.room.server.user.UserEntity
import com.trip.my.room.server.user.UserRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("daim")
class PlaceTest(@Autowired private val placeRepository: PlaceRepository,
				@Autowired private val userRepository: UserRepository,
				@Autowired private val countryRepository: CountryRepository,
				@Autowired private val placeMapper: PlaceMapper
) {
	
	lateinit var userId: UUID
	
//	@BeforeAll
//	fun beforeAll(){
//		var newUser = UserEntity().apply {
//			this.name = "testUser"
//		}
//		userRepository.save(newUser)
//		userId = newUser.id!!
//
//		// 10개 정도의 나라를 생성
//		for (idx in 1..11){
//			var newCountry = CountryEntity().apply {
//				this.name = "Testcountry${idx}"
//			}
//			countryRepository.save(newCountry)
//
//			var newPlace = PlaceEntity().apply {
//				this.country = newCountry
//				this.name = "Testplace${idx}"
//				this.country = newCountry
//			}
//
//			if (idx % 3 == 0){
//				newPlace.user = newUser
//				newPlace.customized = true
//			}
//			placeRepository.save(newPlace)
//		}
//	}
//
//	@Test
//	fun getAllPlaces(){
//		var placeResult = placeRepository.findAllByUserId(userId)
//		val result = placeMapper.toDtoList(placeResult!!)
//		println(result)
//	}
//
//	@Test
//	fun searchPlace(){
//		val placeStr = "place1"
//		val placeResult = placeRepository.findByPlaceName(placeStr)
//		val result = placeMapper.toDtoList(placeResult!!)
//		println(result)
//	}
}
