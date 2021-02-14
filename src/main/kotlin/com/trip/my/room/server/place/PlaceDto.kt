package com.trip.my.room.server.place

import java.util.*

class PlaceDto {

    // Place 생성 시 사용
    open class PlaceIn {
        var name: String? = null
        var latitude: String? = null
        var longtitude: String? = null
    }

    // Place 조회 시 사용
    open class PlaceOut {
        var id: UUID? = null
        var name: String? = null
        var latitude: String? = null
        var longtitude: String? = null
    }

}
