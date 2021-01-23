package com.trip.my.room.server.story

import com.trip.my.room.server.picture.PictureResponseDto
import java.time.LocalDateTime

class StoryResponseDto(
    val id: Long,
    val date: LocalDateTime,
    val memo: String,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    val userPlaceId: Long,
    val pictureList: List<PictureResponseDto>
) {}
