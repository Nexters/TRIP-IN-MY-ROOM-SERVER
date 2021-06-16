package com.trip.my.room.server.domain.story

import java.time.LocalDateTime

data class StoryPatchRequestDto(
    val title: String,
    val date: LocalDateTime,
    val memo: String,
) {}
