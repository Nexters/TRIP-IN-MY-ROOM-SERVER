package com.trip.my.room.server.story.controller

import com.trip.my.room.server.story.controller.dto.StoryCreateRequestDto
import com.trip.my.room.server.story.controller.dto.StoryPatchRequestDto
import com.trip.my.room.server.story.controller.dto.StoryResponseDto
import com.trip.my.room.server.story.service.StoryService
import com.trip.my.room.server.user.IfUserPrincipal
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/stories")
class StoryController(private val storyService: StoryService) {

    @GetMapping
    fun getAllStories(@AuthenticationPrincipal principal: IfUserPrincipal): List<StoryResponseDto> {
        val userId = principal.getUserUUID()
        return storyService.getAllStoriesByUserId(userId)
    }

    @GetMapping("/{id}")
    fun getStory(@PathVariable id: UUID): StoryResponseDto {
        return storyService.getStoriesById(id)
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    fun createNewStory(
        @AuthenticationPrincipal principal: IfUserPrincipal,

        @RequestParam("title") title: String,

        @RequestParam("date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
        date: LocalDateTime,

        @RequestParam("memo") memo: String,
        @RequestParam("experiencePlace") experiencePlace: String,
        @RequestParam("pictures") multipartFiles: List<MultipartFile>,
    ) {
        val userId = principal.getUserUUID()
        val storyCreateRequestDto = StoryCreateRequestDto(title, date, memo, experiencePlace)

        storyService.createNewStory(userId, multipartFiles, storyCreateRequestDto)
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
