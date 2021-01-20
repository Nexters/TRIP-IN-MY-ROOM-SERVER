package com.trip.my.room.server.controller.story

import java.time.LocalDateTime

class StoryResponseDto(
    val id: Long,
    val date: LocalDateTime,
    val memo: String,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    val userPlaceId: Long
) {}
