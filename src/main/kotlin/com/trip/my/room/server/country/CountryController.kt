package com.trip.my.room.server.country

import com.trip.my.room.server.user.IfUserPrincipal
import io.swagger.annotations.ApiOperation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/countries")
class CountryController(private val countryService: CountryService) {
	
	@ApiOperation("country 정보 가져오기 (others 옵션으로 내가 설정한 country함께 조회)")
	@GetMapping
	fun getCountriesByUserId(@AuthenticationPrincipal principal: IfUserPrincipal, @RequestParam("others", defaultValue = "false") others: Boolean): List<CountryDto.CountryOut> {
		return countryService.getCountriesByUserId(principal.getUserUUID(), others)
	}
	
	@GetMapping("/stories")
	fun getPlaceWithStoryCount(@AuthenticationPrincipal principal: IfUserPrincipal): List<CountryStoryCountResponseDto> {
		return countryService.getPlaceWithStoryCount(principal.getUserUUID())
	}
}
