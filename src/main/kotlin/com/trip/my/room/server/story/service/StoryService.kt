package com.trip.my.room.server.story.service

import com.trip.my.room.server.picture.service.PictureService
import com.trip.my.room.server.story.controller.dto.StoryCreateRequestDto
import com.trip.my.room.server.story.controller.dto.StoryPatchRequestDto
import com.trip.my.room.server.story.controller.dto.StoryResponseDto
import com.trip.my.room.server.story.domain.repository.StoryRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.ZoneOffset
import java.util.*
import javax.transaction.Transactional
import kotlin.streams.toList

@Service
class StoryService(
    private val storyRepository: StoryRepository,
    private val pictureService: PictureService
) {
    fun getAllStoriesByUserId(userId: UUID): List<StoryResponseDto> {
        val storyEntityList = storyRepository.findByUserId(userId)

        if (storyEntityList.isEmpty()) {
            return emptyList()
        }

        return storyEntityList.stream()
            .map { storyEntity ->
                StoryResponseDto(
                    storyEntity.id,
                    storyEntity.date,
                    storyEntity.memo,
                    storyEntity.createdAt,
                    storyEntity.updatedAt,
                    storyEntity.experiencePlace,
                    pictureService.getPictureListByStoryId(storyEntity.id)
                )
            }
            .toList()
    }

    fun getStoriesById(storyId: UUID): StoryResponseDto {
        val foundStoryEntity = storyRepository.findById(storyId).orElseThrow()
        val foundPictureResponseDto = pictureService.getPictureListByStoryId(storyId)

        return StoryResponseDto(
            foundStoryEntity.id,
            foundStoryEntity.date,
            foundStoryEntity.memo,
            foundStoryEntity.createdAt,
            foundStoryEntity.updatedAt,
            foundStoryEntity.experiencePlace,
            foundPictureResponseDto
        )
    }

    @Transactional
    fun createNewStory(
        userId: UUID,
        multipartFiles: List<MultipartFile>,
        storyCreateRequestDto: StoryCreateRequestDto
    ) {
        val storyEntity = storyCreateRequestDto.toEntity(userId)
        val savedStoryEntity = storyRepository.save(storyEntity)

        val storyId = savedStoryEntity.id
        println("storyId: $storyId")

        // TODO: Implement and mix user_place_relation logic

        pictureService.createNewPicture(savedStoryEntity, multipartFiles)
    }

    @Transactional
    fun patchStory(
        storyId: UUID,
        multipartFiles: List<MultipartFile>,
        storyPatchRequestDto: StoryPatchRequestDto
    ) {
        val foundStory = storyRepository.findById(storyId).orElseThrow()

        foundStory.update(
            storyPatchRequestDto.title,
            storyPatchRequestDto.date.toInstant(ZoneOffset.UTC),
            storyPatchRequestDto.memo,
            storyPatchRequestDto.experiencePlace
        )

        pictureService.deletePictureByStoryId(storyId)
        pictureService.createNewPicture(foundStory, multipartFiles)
    }

    @Transactional
    fun deleteStory(storyId: UUID) {
        pictureService.deletePictureByStoryId(storyId)
        storyRepository.deleteById(storyId)
    }

}
