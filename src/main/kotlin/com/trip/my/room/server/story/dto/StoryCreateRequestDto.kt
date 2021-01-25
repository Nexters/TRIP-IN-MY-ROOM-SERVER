package com.trip.my.room.server.story.dto

import java.time.LocalDateTime

class StoryCreateRequestDto (
    val date: LocalDateTime,
    val memo: String,
    val placeName: String
) {}
