package com.trip.my.room.server.story.controller.dto

import com.trip.my.room.server.story.domain.model.StoryEntity
import java.time.LocalDateTime

class StoryCreateRequestDto(
    val date: LocalDateTime,
    val memo: String,
    val placeName: String
) {

    fun toEntity(): StoryEntity {
        
    }
}
