package com.trip.my.room.server.story.controller

import com.trip.my.room.server.common.enum.PictureOrder
import com.trip.my.room.server.picture.controller.PictureResponseDto
import com.trip.my.room.server.story.controller.dto.StoryCreateRequestDto
import com.trip.my.room.server.story.controller.dto.StoryPatchRequestDto
import com.trip.my.room.server.story.controller.dto.StoryResponseDto
import com.trip.my.room.server.story.service.StoryService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/stories")
class StoryController(private val storyService: StoryService) {

    @GetMapping
    fun getAllStories(): List<StoryResponseDto> {
        // TODO: Get userId from login information (session or token)
//        storyService.getAllStoriesByUserId(UUID.randomUUID())

        val pictureResponseDto = PictureResponseDto(
            UUID.randomUUID(),
            PictureOrder.ONE.getValue(),
            "https://hello.world/temp.jpg",
            "temp.jpg",
            UUID.randomUUID()
        )

        return listOf(
            StoryResponseDto(
                UUID.randomUUID(),
                Instant.now(),
                "memo",
                Instant.now(),
                Instant.now(),
                "우리집",
                listOf(pictureResponseDto)
            )
        )
    }

    @GetMapping("/{id}")
    fun getStory(@PathVariable id: UUID): StoryResponseDto {
        return storyService.getStoriesById(id)
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    fun createNewStory(
        @RequestParam("title") title: String,

        @RequestParam("date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
        date: LocalDateTime,

        @RequestParam("memo") memo: String,
        @RequestParam("experiencePlace") experiencePlace: String,
        @RequestParam("pictures") multipartFiles: List<MultipartFile>,
    ) {
        val storyCreateRequestDto = StoryCreateRequestDto(title, date, memo, experiencePlace)

        // TODO: Get userId and insert
        storyService.createNewStory(UUID.randomUUID(), multipartFiles, storyCreateRequestDto)
    }

    @PatchMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    fun patchStory(
        @PathVariable id: UUID,
        @RequestParam("title") title: String,

        @RequestParam("date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
        date: LocalDateTime,

        @RequestParam("memo") memo: String,
        @RequestParam("experiencePlace") experiencePlace: String,
        @RequestParam("pictures") multipartFiles: List<MultipartFile>,
    ) {
        val storyPatchRequestDto = StoryPatchRequestDto(title, date, memo, experiencePlace)

        println("story id=$id, patchDto=$storyPatchRequestDto")
        storyService.patchStory(id, multipartFiles, storyPatchRequestDto)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    fun deleteStory(@PathVariable id: UUID) {
        storyService.deleteStory(id)
    }
}
