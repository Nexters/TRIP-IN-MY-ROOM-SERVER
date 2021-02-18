package com.trip.my.room.server.place

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity(name = "place")
class PlaceEntity {

    @field: Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    var id: UUID? = null

    var name: String? = null

    // 위도
    var latitude: Double? = null

    // 경도
    var longitude: Double? = null

}
