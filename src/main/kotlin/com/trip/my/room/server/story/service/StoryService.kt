package com.trip.my.room.server.story.service

import com.trip.my.room.server.picture.PictureService
import com.trip.my.room.server.story.controller.dto.StoryCreateRequestDto
import com.trip.my.room.server.story.domain.repository.StoryRepository
import org.springframework.stereotype.Service

@Service
class StoryService (private val pictureService: PictureService,
                    private val storyRepository: StoryRepository) {

    fun createNewStory(storyCreateRequestDto: StoryCreateRequestDto) : Nothing {
        // story 만들고


        storyRepository.save()

        // 해당 id와 함께 사진 업로드
        // 그리고 리턴
    }

}
