package com.trip.my.room.server.country

import com.trip.my.room.server.user.IfUserPrincipal
import io.swagger.annotations.ApiOperation
import mu.KLogging
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/countries")
class CountryController(private val countryService: CountryService) {

    companion object : KLogging()

    @ApiOperation("country 정보 가져오기 (others 옵션으로 내가 설정한 country함께 조회)")
    @GetMapping
    fun getCountriesByUserId(
        @AuthenticationPrincipal principal: IfUserPrincipal,
        @RequestParam("others", defaultValue = "false") others: Boolean
    ): List<CountryDto.CountryOut> {
        val userId = principal.getUserUUID()
        val countriesByUserId = countryService.getCountriesByUserId(userId, others)

        logger.info { "userId=$userId countriesByUserId=$countriesByUserId" }

        return countriesByUserId
    }

    @GetMapping("/stories")
    fun getPlaceWithStoryCount(@AuthenticationPrincipal principal: IfUserPrincipal): List<CountryStoryCountResponseDto> {
        val userId = principal.getUserUUID()
        val placeWithStoryCount = countryService.getPlaceWithStoryCount(principal.getUserUUID())

        logger.info { "userId=$userId placeWithStoryCount=$placeWithStoryCount" }

        return placeWithStoryCount
    }

    @GetMapping("/search")
    fun searchCountries(
        @AuthenticationPrincipal principal: IfUserPrincipal,
        @RequestParam("countryName") countryName: String
    ): List<CountryResponseDto> {
        val userId = principal.getUserUUID()
        val searchByCountryName = countryService.searchByCountryName(principal.getUserUUID(), countryName)

        logger.info { "userId=$userId, countryName=$countryName, searchByCountryName=$searchByCountryName" }

        return searchByCountryName
    }
}
