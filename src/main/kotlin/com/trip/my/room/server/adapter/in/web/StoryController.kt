package com.trip.my.room.server.adapter.`in`.web

import com.fasterxml.jackson.annotation.JsonFormat
import com.trip.my.room.server.application.service.StoryService
import com.trip.my.room.server.domain.picture.PictureRequestDto
import com.trip.my.room.server.domain.story.StoryCreateRequestDto
import com.trip.my.room.server.domain.story.StoryDetailResponseDto
import com.trip.my.room.server.domain.story.StoryPatchRequestDto
import com.trip.my.room.server.domain.story.StoryResponseDto
import com.trip.my.room.server.place.PlaceDto
import com.trip.my.room.server.user.IfUserPrincipal
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*

@RestController
class StoryController(private val storyService: StoryService) {

    companion object : KLogging()

    @GetMapping("/stories")
    fun getAllStories(@AuthenticationPrincipal principal: IfUserPrincipal): List<StoryResponseDto> {
        val userId = principal.getUserUUID()
        return storyService.getAllStoriesByUserId(userId)
    }

    @GetMapping("/stories/{id}")
    fun getStory(@PathVariable id: UUID): StoryDetailResponseDto {
        return storyService.getStoriesById(id)
    }

    @PostMapping("/stories")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    fun createNewStory(
        @AuthenticationPrincipal principal: IfUserPrincipal,
        @RequestBody createStoryForm: CreateStoryForm
    ) {
        val userId = principal.getUserUUID()
        val storyCreateRequestDto = StoryCreateRequestDto(createStoryForm.title, createStoryForm.date,createStoryForm.memo)
        val placeInDto = PlaceDto.PlaceIn(createStoryForm.placeName, createStoryForm.placeLatitude, createStoryForm.placeLongitude)

        logger.info { "[createNewStory] userId=$userId, storyCreateRequestDto=$storyCreateRequestDto, placeDto=$placeInDto, pictureDtoList=${createStoryForm.pictureRequestDtoList}" }

        storyService.createNewStory(
            userId,
            createStoryForm.pictureRequestDtoList,
            storyCreateRequestDto,
            createStoryForm.countryId,
            createStoryForm.newCountryName,
            placeInDto
        )
    }

    @PatchMapping("/stories/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    fun patchStory(
        @AuthenticationPrincipal principal: IfUserPrincipal,
        @PathVariable id: UUID,
        @RequestBody updateStoryForm: UpdateStoryForm
    ) {
        val userId = principal.getUserUUID()
        val storyPatchRequestDto = StoryPatchRequestDto(updateStoryForm.title, updateStoryForm.date, updateStoryForm.memo)
        val placeResponseDto = PlaceDto.PlaceIn(updateStoryForm.placeName, updateStoryForm.placeLatitude, updateStoryForm.placeLongitude)

        logger.info { "[patchStory] userId=$userId storyPatchRequestDto=$storyPatchRequestDto placeDto=$placeResponseDto" }

        storyService.patchStory(
            userId,
            id,
            updateStoryForm.pictureRequestDtoList,
            storyPatchRequestDto,
            updateStoryForm.countryId,
            updateStoryForm.newCountryName,
            updateStoryForm.placeId,
            placeResponseDto
        )
    }

    @DeleteMapping("/stories/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    fun deleteStory(@PathVariable id: UUID) {
        storyService.deleteStory(id)
    }


    @GetMapping("/stories/countries/{countryType}")
    fun getStoriesGroupByCountryType(
        @AuthenticationPrincipal principal: IfUserPrincipal,
        @PathVariable("countryType") countryType: String
    ): List<StoryResponseDto> {
        val userId = principal.getUserUUID()

        logger.info { "[getStoriesGroupByCountryType] userId=$userId countryType=$countryType" }

        return storyService.getStoriesByCountryType(userId, countryType.toUpperCase())
    }

    data class CreateStoryForm(
        val title: String,
        val memo: String,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        val date: LocalDateTime,

        val pictureRequestDtoList: List<PictureRequestDto>,

        // TODO countryId 유무에 따라서 newCountryName valid 하기
        val countryId: UUID?,
        val newCountryName: String?,

        val placeName: String,
        val placeLatitude: Double,
        val placeLongitude: Double
    )

    data class UpdateStoryForm(
        val title: String,
        val memo: String,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        val date: LocalDateTime,


        val pictureRequestDtoList: List<PictureRequestDto>,

        // TODO countryId 유무에 따라서 newCountryName valid 하기
        val countryId: UUID?,
        val newCountryName: String?,

        val placeId: UUID,
        val placeName: String,
        val placeLatitude: Double,
        val placeLongitude: Double
    )

}
