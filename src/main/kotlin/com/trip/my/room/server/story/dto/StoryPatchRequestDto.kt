package com.trip.my.room.server.story.dto

import com.trip.my.room.server.picture.PictureResponseDto
import java.time.LocalDateTime

class StoryPatchRequestDto (
    val date: LocalDateTime,
    val memo: String,
    val placeName: String,
    val pictureList: List<PictureResponseDto>
) {}
