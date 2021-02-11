package com.trip.my.room.server.story.controller.dto

import com.trip.my.room.server.story.domain.model.StoryEntity
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

data class StoryCreateRequestDto(
    val title: String,
    val date: LocalDateTime,
    val memo: String,
    val experiencePlace: String
) {
    private val KOREA_ZONE_OFFSET = "+09:00"

    fun toEntity(userId: UUID): StoryEntity {
        return StoryEntity(
            title,
            date.toInstant(ZoneOffset.of(KOREA_ZONE_OFFSET)),
            memo,
            experiencePlace,
            userId
        )
    }
}
