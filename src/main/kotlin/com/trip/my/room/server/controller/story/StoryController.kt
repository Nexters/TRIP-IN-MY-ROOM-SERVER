package com.trip.my.room.server.controller.story

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class StoryController {

    @GetMapping("/stories")
    fun getStories(): StoryResponseDto {
        return StoryResponseDto(1, LocalDateTime.now(), "memo", LocalDateTime.now(), LocalDateTime.now(), 1)
    }
}
