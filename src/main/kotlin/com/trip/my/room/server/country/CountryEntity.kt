package com.trip.my.room.server.country

import com.trip.my.room.server.user.UserEntity
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity(name = "country")
class CountryEntity {

    @field:Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    var id: UUID? = null

    var name: String? = null

    var type: String? = null

    // TODO default image url 넣기
    var flagImageUrl: String? = null

    var letterImageUrl: String? = null

    @field: ManyToOne(fetch = FetchType.LAZY)
    @field: JoinColumn(name = "user_id", nullable = true, columnDefinition = "BINARY(16)")
    var user: UserEntity? = null

}
