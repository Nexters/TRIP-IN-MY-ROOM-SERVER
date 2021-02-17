package com.trip.my.room.server.country

import com.trip.my.room.server.user.UserEntity
import java.util.*
import javax.persistence.*

@Entity(name = "country")
class CountryEntity {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null

    var name: String? = null

    var type: String? = null

    // TODO default image url 넣기
    var flagImageUrl: String? = null

    var letterImageUrl: String? = null

    @field: ManyToOne(fetch = FetchType.LAZY)
    @field: JoinColumn(name = "user_id", nullable = true)
    var user: UserEntity? = null

}
