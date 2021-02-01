package com.trip.my.room.server.story.controller.dto

import com.trip.my.room.server.picture.PictureResponseDto
import java.time.Instant

data class StoryPatchRequestDto (
    val title: String,
    val date: Instant,
    val memo: String,
    val experiencePlace: String,
    var pictureList: List<PictureResponseDto>?
) {}
