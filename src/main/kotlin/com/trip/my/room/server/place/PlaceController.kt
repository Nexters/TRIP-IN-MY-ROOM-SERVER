package com.trip.my.room.server.place

import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/place")
class PlaceController(@Autowired private val placeService: PlaceService) {
    
    @ApiOperation("place 정보 가져오기")
    @GetMapping
    fun getPlace(): MutableList<PlaceEntity> {
        return placeService.getPlaces()
    }

    @GetMapping("/search")
    fun getPlaceBySearch(): String {
        return "call getPlaceBySearch()"
    }

}
