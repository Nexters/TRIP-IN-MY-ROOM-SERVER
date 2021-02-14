package com.trip.my.room.server.story.controller.dto

import com.trip.my.room.server.picture.controller.PictureResponseDto
import java.time.Instant
import java.util.*

data class StoryResponseDto(
    val id: UUID?,
    val date: Instant?,
    val memo: String?,
    val createdAt: Instant?,
    var updatedAt: Instant?,
    val experiencePlace: String?,
    val pictureList: List<PictureResponseDto>
) {}
