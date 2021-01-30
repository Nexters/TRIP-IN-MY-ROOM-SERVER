package com.trip.my.room.server.story.controller

import com.trip.my.room.server.picture.PictureResponseDto
import com.trip.my.room.server.story.controller.dto.StoryCreateRequestDto
import com.trip.my.room.server.story.controller.dto.StoryPatchRequestDto
import com.trip.my.room.server.story.controller.dto.StoryResponseDto
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/stories")
class StoryController {

    @GetMapping
    fun getStories(): List<StoryResponseDto> {
        val pictureResponseDto = PictureResponseDto(1, 1, "https://hello.world/temp.jpg", "temp.jpg", 1)

        return listOf(
            StoryResponseDto(
                1,
                LocalDateTime.now(),
                "memo",
                LocalDateTime.now(),
                LocalDateTime.now(),
                1,
                listOf(pictureResponseDto)
            )
        )
    }

    @PostMapping
    fun createNewStory(@RequestBody storyCreateRequestDto: StoryCreateRequestDto) {
        // TODO 파일 어떻게 받을지 생각. order라는 개념과 함께 매핑할 수 있는지.
        // Story db 저장 -> 사진 업로드 -> 사진 db 저장 (사진 업로드 정보 + story id)
        println(storyCreateRequestDto)
    }

    @PatchMapping("/{id}")
    fun patchStory(@PathVariable id: Long, @RequestBody storyPatchRequestDto: StoryPatchRequestDto) {
        // TODO: 사진을 삭제 생각, 사진 새로 업로드 생각.
        println(storyPatchRequestDto)
    }

    @DeleteMapping("/{id}")
    fun deleteStory(@PathVariable id: Long) {
        println("delete story. id=$id")
    }
}
