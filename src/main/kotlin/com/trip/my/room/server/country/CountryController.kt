package com.trip.my.room.server.country

import com.trip.my.room.server.place.PlaceService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/country")
class CountryController(@Autowired private val countryService: CountryService,
						@Autowired private val placeService: PlaceService
) {
	
	@ApiOperation("Country 리스트 정보 가져오기")
	@GetMapping
	fun getCounties(): List<CountryDto.CountryOut> {
		return countryService.getCountries()
	}
}