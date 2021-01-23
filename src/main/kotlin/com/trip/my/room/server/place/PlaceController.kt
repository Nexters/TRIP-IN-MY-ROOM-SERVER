package com.trip.my.room.server.place

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/place")
class PlaceController {

    @GetMapping
    fun getPlace(): String {
        return "call getPlace()"
    }

    @GetMapping("/search")
    fun getPlaceBySearch(): String {
        return "call getPlaceBySearch()"
    }

}
