package com.trip.my.room.server.story.domain.model

import com.trip.my.room.server.common.entity.TimeEntity
import com.trip.my.room.server.place.RelUserPlaceEntity
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity(name = "story")
class StoryEntity(title: String,
                  date: Instant,
                  memo: String,
                  userId: UUID) : TimeEntity() {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null

    var userId: UUID? = userId

    var title: String? = title

    var date: Instant? = date

    var memo: String? = memo

    @field: LastModifiedDate
    var updatedAt: Instant? = null

    @field: ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rel_user_place_id")
    var userPlace: RelUserPlaceEntity? = null
}
