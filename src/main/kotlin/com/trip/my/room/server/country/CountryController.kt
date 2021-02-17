package com.trip.my.room.server.country

import com.trip.my.room.server.user.IfUserPrincipal
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/countries")
class CountryController(private val countryService: CountryService) {

    @GetMapping("/stories")
    fun getPlaceWithStoryCount(@AuthenticationPrincipal principal: IfUserPrincipal): List<CountryStoryCountResponseDto> {
        val userId = principal.getUserUUID()

        return countryService.getPlaceWithStoryCount(userId)
    }
}
