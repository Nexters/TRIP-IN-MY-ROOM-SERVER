package com.trip.my.room.server.story

import java.time.LocalDateTime

class StoryPatchRequestDto (
    val date: LocalDateTime,
    val memo: String,
    val placeName: String,
    val pictureList: List<>
) {}
