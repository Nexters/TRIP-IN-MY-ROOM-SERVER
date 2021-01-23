package com.trip.my.room.server.story

import java.time.LocalDateTime

class StoryCreateRequestDto (
    val date: LocalDateTime,
    val memo: String,
    val placeName: String
) {}
