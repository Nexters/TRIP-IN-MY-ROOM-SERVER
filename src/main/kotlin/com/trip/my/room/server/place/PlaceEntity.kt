package com.trip.my.room.server.place

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "place")
class PlaceEntity {

    @field: Id
    @field: GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null

    var name: String? = null

    // 위도
    var latitude: String? = null

    // 경도
    var longtitude: String? = null

}
