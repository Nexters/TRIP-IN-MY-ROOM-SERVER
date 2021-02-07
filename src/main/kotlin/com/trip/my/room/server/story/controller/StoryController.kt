package com.trip.my.room.server.story.controller

import com.trip.my.room.server.picture.controller.PictureResponseDto
import com.trip.my.room.server.story.controller.dto.StoryCreateRequestDto
import com.trip.my.room.server.story.controller.dto.StoryPatchRequestDto
import com.trip.my.room.server.story.controller.dto.StoryResponseDto
import com.trip.my.room.server.story.service.StoryService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/stories")
class StoryController(private val storyService: StoryService) {

    @GetMapping
    fun getAllStories(): List<StoryResponseDto> {
        // TODO: Get userId from login information (session or token)
        // storyService.getStoriesByUserId(UUID(1, 1))

        val pictureResponseDto = PictureResponseDto(1, 1, "https://hello.world/temp.jpg", "temp.jpg", 1)

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
        // storyService.getStoriesById(id)

        val pictureResponseDto = PictureResponseDto(1, 1, "https://hello.world/temp.jpg", "temp.jpg", 1)

        return StoryResponseDto(
            UUID.randomUUID(),
            Instant.now(),
            "memo",
            Instant.now(),
            Instant.now(),
            "우리집",
            listOf(pictureResponseDto)
        )
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    fun createNewStory(@RequestBody storyCreateRequestDto: StoryCreateRequestDto) {
        // TODO 파일 어떻게 받을지 생각. order라는 개념과 함께 매핑할 수 있는지.
        // Story db 저장 -> 사진 업로드 -> 사진 db 저장 (사진 업로드 정보 + story id)
        println("$storyCreateRequestDto")
        storyService.createNewStory(UUID.randomUUID(), storyCreateRequestDto)
    }

    @PatchMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    fun patchStory(@PathVariable id: UUID, @RequestBody storyPatchRequestDto: StoryPatchRequestDto) {
        // TODO: 사진을 삭제 생각, 사진 새로 업로드 생각.
        println("story id=$id, patchDto=$storyPatchRequestDto")
        storyService.patchStory(id, storyPatchRequestDto)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    fun deleteStory(@PathVariable id: UUID) {
        println("delete story. id=$id")
        storyService.deleteStory(id)
    }
}
