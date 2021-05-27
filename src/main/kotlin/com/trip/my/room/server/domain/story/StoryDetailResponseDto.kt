package com.trip.my.room.server.domain.story

import com.trip.my.room.server.country.CountryResponseDto
import com.trip.my.room.server.domain.picture.PictureResponseDto
import com.trip.my.room.server.place.PlaceDto
import java.time.Instant
import java.util.*

data class StoryDetailResponseDto(
    val id: UUID?,
    val date: Instant?,
    val title: String?,
    val memo: String?,
    val createdAt: Instant?,
    var updatedAt: Instant?,
    val userId: UUID?,
    val pictureList: List<PictureResponseDto>,
    val place: PlaceDto.PlaceOut,
    val country: CountryResponseDto
) {}