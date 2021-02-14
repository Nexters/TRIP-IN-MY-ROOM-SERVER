package com.trip.my.room.server.story.domain.model

import com.trip.my.room.server.common.entity.TimeEntity
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity(name = "story")
class StoryEntity(
    title: String,
    date: Instant,
    memo: String,
    experiencePlace: String,
    userId: UUID
) : TimeEntity() {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null

    var title: String? = title

    var date: Instant? = date

    var memo: String? = memo

    var experiencePlace: String? = experiencePlace

    @field: LastModifiedDate
    var updatedAt: Instant? = null

    var userId: UUID? = userId

    fun update(title: String, date: Instant, memo: String, experiencePlace: String) {
        this.title = title
        this.date = date
        this.memo = memo
        this.experiencePlace = experiencePlace
    }
}
