package com.trip.my.room.server.domain.story

import com.trip.my.room.server.country.CountryDto
import com.trip.my.room.server.domain.picture.PictureResponseDto
import java.time.Instant
import java.util.*

data class StoryResponseDto(
    val id: UUID?,
    val date: Instant?,
    val title: String?,
    val memo: String?,
    val createdAt: Instant?,
    var updatedAt: Instant?,
    val userId: UUID?,
    val country: CountryDto.CountryOut,
    val pictureList: List<PictureResponseDto>
) {}
