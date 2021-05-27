package com.trip.my.room.server.domain.story.service

import com.trip.my.room.server.adapter.out.persistence.jpa.PictureJpaAdapter
import com.trip.my.room.server.country.CountryMapper
import com.trip.my.room.server.country.CountryService
import com.trip.my.room.server.domain.picture.PictureRequestDto
import com.trip.my.room.server.domain.story.StoryCreateRequestDto
import com.trip.my.room.server.domain.story.StoryDetailResponseDto
import com.trip.my.room.server.domain.story.StoryPatchRequestDto
import com.trip.my.room.server.domain.story.StoryResponseDto
import com.trip.my.room.server.domain.story.domain.model.StoryEntity
import com.trip.my.room.server.domain.story.domain.repository.StoryRepository
import com.trip.my.room.server.place.PlaceDto
import com.trip.my.room.server.place.PlaceService
import org.springframework.stereotype.Service
import java.time.ZoneOffset
import java.util.*
import javax.transaction.Transactional
import kotlin.streams.toList

@Service
class StoryService(
    private val storyRepository: StoryRepository,
    private val pictureJpaAdapter: PictureJpaAdapter,
    private val placeService: PlaceService,
    private val countryService: CountryService,
    private val countryMapper: CountryMapper
) {
    fun getAllStoriesByUserId(userId: UUID): List<StoryResponseDto> {
        val storyEntityList = storyRepository.findByUserId(userId)

        return storyEntityList.stream()
            .map { storyEntity -> convertStoryResponseDto(storyEntity) }
            .sorted { o1, o2 -> o2.createdAt!!.compareTo(o1.createdAt) }
            .toList()
    }

    fun getStoriesById(storyId: UUID): StoryDetailResponseDto {
        val foundStoryEntity = storyRepository.findById(storyId)
            .orElseThrow { throw NoSuchElementException("해당 하는 user 정보가 없습니다.") }
        val foundPictureResponseDto = pictureJpaAdapter.findAllPictureByStoryId(storyId)
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
        pictureRequestDtoList: List<PictureRequestDto>,
        storyCreateRequestDto: StoryCreateRequestDto,
        countryId: UUID?,
        newCountryName: String?,
        placeInDto: PlaceDto.PlaceIn
    ) {
        val placeEntity = placeService.getPlaceEntityByPlaceInDto(placeInDto)
        val countryEntity = getCountryEntityByUsingCountryId(countryId, newCountryName, userId)
        val storyEntity = storyCreateRequestDto.toEntity(userId, placeEntity, countryEntity)

        val savedStoryEntity = storyRepository.save(storyEntity)

        pictureJpaAdapter.createPicture(savedStoryEntity, pictureRequestDtoList)
    }

    @Transactional
    fun patchStory(
        userId: UUID,
        storyId: UUID,
        pictureRequestDtoList: List<PictureRequestDto>,
        storyPatchRequestDto: StoryPatchRequestDto,
        countryId: UUID?,
        newCountryName: String?,
        placeId: UUID?,
        placeInDto: PlaceDto.PlaceIn
    ) {
        val foundStory = storyRepository.findById(storyId).orElseThrow()

        foundStory.update(
            storyPatchRequestDto.title,
            storyPatchRequestDto.date.toInstant(ZoneOffset.UTC),
            storyPatchRequestDto.memo
        )

        val placeEntity = placeService.findPlaceEntityIfExistIdOrNotMakeNewEntity(placeId, placeInDto)
        if (!foundStory.equals(placeEntity)) {
            foundStory.updatePlace(placeEntity)
        }

        val countryEntity = getCountryEntityByUsingCountryId(countryId, newCountryName, userId)
        if (!foundStory.equals(countryEntity)) {
            foundStory.updateCountry(countryEntity)
        }

        pictureJpaAdapter.deletePictureByStorageKey(storyId)
        pictureJpaAdapter.createPicture(foundStory, pictureRequestDtoList)
    }

    private fun getCountryEntityByUsingCountryId(countryId: UUID?, newCountryName: String?, userId: UUID) =
        if (countryId == null) countryService.createNewOtherCountry(newCountryName, userId)
        else countryService.getByCountryId(countryId)

    @Transactional
    fun deleteStory(storyId: UUID) {
        pictureJpaAdapter.deletePictureByStorageKey(storyId)
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
            storyEntity.title,
            storyEntity.memo,
            storyEntity.createdAt,
            storyEntity.updatedAt,
            storyEntity.userId,
            countryMapper.toDto(storyEntity.country!!),
            pictureJpaAdapter.findAllPictureByStoryId(storyEntity.id)
        )
    }
}
