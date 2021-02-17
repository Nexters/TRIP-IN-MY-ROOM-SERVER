package com.trip.my.room.server.story.controller.dto

import java.time.LocalDateTime

data class StoryPatchRequestDto(
    val title: String,
    val date: LocalDateTime,
    val memo: String,
) {}
