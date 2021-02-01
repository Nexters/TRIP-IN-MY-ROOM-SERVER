package com.trip.my.room.server.story.controller.dto

import com.trip.my.room.server.story.domain.model.StoryEntity
import java.time.Instant
import java.util.*

data class StoryCreateRequestDto(
    val title: String,
    val date: Instant,
    val memo: String,
    val experiencePlace: String
) {

    fun toEntity(userId: UUID): StoryEntity {
        return StoryEntity(title, date, memo, experiencePlace, userId)
    }
}
