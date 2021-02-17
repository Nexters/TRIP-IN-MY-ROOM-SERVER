package com.trip.my.room.server.story.controller.dto

import com.trip.my.room.server.country.CountryEntity
import com.trip.my.room.server.place.PlaceEntity
import com.trip.my.room.server.story.domain.model.StoryEntity
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

data class StoryCreateRequestDto(
    val title: String,
    val date: LocalDateTime,
    val memo: String
) {
    private val KOREA_ZONE_OFFSET = "+09:00"

    fun toEntity(userId: UUID, placeEntity: PlaceEntity, countryEntity: CountryEntity): StoryEntity {
        return StoryEntity(
            title,
            date.toInstant(ZoneOffset.of(KOREA_ZONE_OFFSET)),
            memo,
            userId,
            placeEntity,
            countryEntity,
            countryEntity.type
        )
    }
}
