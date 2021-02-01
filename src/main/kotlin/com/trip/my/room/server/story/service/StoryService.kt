package com.trip.my.room.server.story.service

import com.trip.my.room.server.picture.PictureService
import com.trip.my.room.server.story.controller.dto.StoryCreateRequestDto
import com.trip.my.room.server.story.domain.repository.StoryRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class StoryService(
    private val pictureService: PictureService,
    private val storyRepository: StoryRepository
) {

    fun getStoriesByUserId(userId: UUID) {
        val storyEntityList = storyRepository.findByUserId(userId)
        println(storyEntityList)
    }

    // TODO: Story db 저장 -> 사진 업로드 -> 사진 db 저장 (사진 업로드 정보 + story id)
    @Transactional
    fun createNewStory(userId: UUID, storyCreateRequestDto: StoryCreateRequestDto) {

        val storyEntity = storyCreateRequestDto.toEntity(userId)
        val savedStoryEntity = storyRepository.save(storyEntity)

        val storyId = savedStoryEntity.id
        println("storyId: $storyId")

        // TODO: Implement and mix user_place_relation logic

        // TODO: 사진 업로드 로직
        // pictureService.createNewPicture(storyId, files)
    }

    // TODO 사진 삭제, 새로운 사진 업로드, 사진 배열 변경 주의
    @Transactional
    fun patchStory() {

    }

    fun deleteStory(storyId: UUID) {
        storyRepository.deleteById(storyId)
    }

}
