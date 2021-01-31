package com.trip.my.room.server.story.controller.dto

import com.trip.my.room.server.story.domain.model.StoryEntity
import java.time.Instant
import java.util.*

class StoryCreateRequestDto(
    val title: String,
    val date: Instant,
    val memo: String,
    val placeName: String
) {

    fun toEntity(): StoryEntity {
        return StoryEntity(title, date, memo, UUID(1, 1))
    }
}
