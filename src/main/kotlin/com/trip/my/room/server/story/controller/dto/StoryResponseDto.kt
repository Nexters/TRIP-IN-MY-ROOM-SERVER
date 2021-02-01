package com.trip.my.room.server.story.controller.dto

import com.trip.my.room.server.picture.controller.PictureResponseDto
import java.time.LocalDateTime

data class StoryResponseDto(
    val id: Long,
    val date: LocalDateTime,
    val memo: String,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    val userPlaceId: Long,
    val pictureList: List<PictureResponseDto>
) {}
