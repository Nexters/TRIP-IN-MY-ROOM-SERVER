package com.trip.my.room.server.story.controller.dto

import com.trip.my.room.server.country.CountryResponseDto
import com.trip.my.room.server.picture.controller.PictureResponseDto
import com.trip.my.room.server.place.PlaceDto
import java.time.Instant
import java.util.*

data class StoryDetailResponseDto(
    val id: UUID?,
    val date: Instant?,
    val memo: String?,
    val createdAt: Instant?,
    var updatedAt: Instant?,
    val userId: UUID?,
    val pictureList: List<PictureResponseDto>,
    val place: PlaceDto.PlaceOut,
    val country: CountryResponseDto
) {}
