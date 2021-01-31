package com.trip.my.room.server.story.service

import com.trip.my.room.server.picture.PictureService
import com.trip.my.room.server.story.controller.dto.StoryCreateRequestDto
import com.trip.my.room.server.story.domain.model.StoryEntity
import com.trip.my.room.server.story.domain.repository.StoryRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class StoryService(private val pictureService: PictureService,
                   private val storyRepository: StoryRepository) {

    fun getStoriesByUserId(userId: UUID) {
        val storyEntityList = storyRepository.findByUserId(userId)
        println(storyEntityList)
    }

    @Transactional
    fun createNewStory(storyCreateRequestDto: StoryCreateRequestDto) {
        // Story db 저장 -> 사진 업로드 -> 사진 db 저장 (사진 업로드 정보 + story id)

        val storyEntity = storyCreateRequestDto.toEntity()
        val savedStoryEntity = storyRepository.save(storyEntity)

        val storyId = savedStoryEntity.id
        // TODO: 사진 업로드
        // pictureService.createNewPicture(storyId, files)
    }

    fun deleteStory(storyId: UUID) {
        storyRepository.deleteById(storyId)
    }

}
