package com.trip.my.room.server

import com.trip.my.room.server.country.CountryEntity
import com.trip.my.room.server.country.CountryRepository
import com.trip.my.room.server.place.PlaceEntity
import com.trip.my.room.server.place.PlaceRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("daim")
class PlaceTest(@Autowired private val placeRepository: PlaceRepository,
				@Autowired private val countryRepository: CountryRepository
) {
	
//	@BeforeAll
//	fun beforeAll(){
//		// 10개 정도의 나라를 생성
//		for (idx in 1..11){
//			var newCountry = CountryEntity().apply {
//				this.name = "country${idx}"
//			}
//			countryRepository.save(newCountry)
//
//		var newPlace = PlaceEntity().apply {
//			this.country = newCountry
//			this.name = "place${idx}"
//		}
//
//	}
}