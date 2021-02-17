package com.trip.my.room.server.story.service

import com.trip.my.room.server.country.CountryService
import com.trip.my.room.server.picture.service.PictureService
import com.trip.my.room.server.place.PlaceDto
import com.trip.my.room.server.place.PlaceService
import com.trip.my.room.server.story.controller.dto.StoryCreateRequestDto
import com.trip.my.room.server.story.controller.dto.StoryDetailResponseDto
import com.trip.my.room.server.story.controller.dto.StoryPatchRequestDto
import com.trip.my.room.server.story.controller.dto.StoryResponseDto
import com.trip.my.room.server.story.domain.model.StoryEntity
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
    private val pictureService: PictureService,
    private val placeService: PlaceService,
    private val countryService: CountryService,
) {
    fun getAllStoriesByUserId(userId: UUID): List<StoryResponseDto> {
        val storyEntityList = storyRepository.findByUserId(userId)

        if (storyEntityList.isEmpty()) {
            return emptyList()
        }

        return storyEntityList.stream()
            .map { storyEntity -> convertStoryResponseDto(storyEntity) }
            .toList()
    }

    fun getStoriesById(storyId: UUID): StoryDetailResponseDto {
        val foundStoryEntity = storyRepository.findById(storyId).orElseThrow()
        val foundPictureResponseDto = pictureService.getPictureListByStoryId(storyId)
        val foundPlaceResponseDto = placeService.getPlaceDtoById(foundStoryEntity.place?.id!!)
        val foundCountryResponseDto = countryService.getCountryResponseDtoById(foundStoryEntity.country?.id!!)

        return StoryDetailResponseDto(
            foundStoryEntity.id,
            foundStoryEntity.date,
            foundStoryEntity.title,
            foundStoryEntity.memo,
            foundStoryEntity.createdAt,
            foundStoryEntity.updatedAt,
            foundStoryEntity.userId,
            foundPictureResponseDto,
            foundPlaceResponseDto,
            foundCountryResponseDto
        )
    }

    @Transactional
    fun createNewStory(
        userId: UUID,
        multipartFiles: List<MultipartFile>,
        storyCreateRequestDto: StoryCreateRequestDto,
        countryId: UUID?,
        newCountryName: String?,
        placeInDto: PlaceDto.PlaceIn
    ) {
        val placeEntity = placeService.getPlaceEntityByPlaceInDto(placeInDto)
        val countryEntity = getCountryEntityByUsingCountryId(countryId, newCountryName, userId)
        val storyEntity = storyCreateRequestDto.toEntity(userId, placeEntity, countryEntity)

        val savedStoryEntity = storyRepository.save(storyEntity)

        println("storyId=${savedStoryEntity.id}")

        pictureService.createNewPicture(savedStoryEntity, multipartFiles)
    }

    @Transactional
    fun patchStory(
        userId: UUID,
        storyId: UUID,
        multipartFiles: List<MultipartFile>,
        storyPatchRequestDto: StoryPatchRequestDto,
        countryId: UUID?,
        newCountryName: String?,
        placeInDto: PlaceDto.PlaceIn
    ) {
        val foundStory = storyRepository.findById(storyId).orElseThrow()

        foundStory.update(
            storyPatchRequestDto.title,
            storyPatchRequestDto.date.toInstant(ZoneOffset.UTC),
            storyPatchRequestDto.memo
        )

        val placeEntity = placeService.getPlaceEntityByPlaceInDto(placeInDto)
        if (!foundStory.equals(placeEntity)) {
            foundStory.updatePlace(placeEntity)
        }

        val countryEntity = getCountryEntityByUsingCountryId(countryId, newCountryName, userId)
        if (!foundStory.equals(countryEntity)) {
            foundStory.updateCountry(countryEntity)
        }

        pictureService.deletePictureByStoryId(storyId)
        pictureService.createNewPicture(foundStory, multipartFiles)
    }

    private fun getCountryEntityByUsingCountryId(countryId: UUID?, newCountryName: String?, userId: UUID) =
        if (countryId == null) countryService.createNewOtherCountry(newCountryName, userId)
        else countryService.getByCountryId(countryId)

    @Transactional
    fun deleteStory(storyId: UUID) {
        pictureService.deletePictureByStoryId(storyId)
        storyRepository.deleteById(storyId)
    }

    fun getStoriesByCountryType(userId: UUID, countryType: String): List<StoryResponseDto> {
        return storyRepository.findByUserIdAndCountryType(userId, countryType).stream()
            .map { storyEntity -> convertStoryResponseDto(storyEntity) }
            .toList()
    }

    private fun convertStoryResponseDto(storyEntity: StoryEntity): StoryResponseDto {
        return StoryResponseDto(
            storyEntity.id,
            storyEntity.date,
            storyEntity.memo,
            storyEntity.createdAt,
            storyEntity.updatedAt,
            storyEntity.userId,
            pictureService.getPictureListByStoryId(storyEntity.id)
        )
    }
}
