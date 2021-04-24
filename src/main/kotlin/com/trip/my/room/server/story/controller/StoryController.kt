package com.trip.my.room.server.story.controller

import com.trip.my.room.server.place.PlaceDto
import com.trip.my.room.server.story.controller.dto.StoryCreateRequestDto
import com.trip.my.room.server.story.controller.dto.StoryDetailResponseDto
import com.trip.my.room.server.story.controller.dto.StoryPatchRequestDto
import com.trip.my.room.server.story.controller.dto.StoryResponseDto
import com.trip.my.room.server.story.service.StoryService
import com.trip.my.room.server.user.IfUserPrincipal
import mu.KLogging
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/stories")
class StoryController(private val storyService: StoryService) {

    companion object : KLogging()

    @GetMapping
    fun getAllStories(@AuthenticationPrincipal principal: IfUserPrincipal): List<StoryResponseDto> {
        val userId = principal.getUserUUID()
        return storyService.getAllStoriesByUserId(userId)
    }

    @GetMapping("/{id}")
    fun getStory(@PathVariable id: UUID): StoryDetailResponseDto {
        return storyService.getStoriesById(id)
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    fun createNewStory(
        @AuthenticationPrincipal principal: IfUserPrincipal,

        @RequestParam("title") title: String,

        @RequestParam("date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
        date: LocalDateTime,

        @RequestParam("memo") memo: String,
        @RequestParam("pictures") multipartFiles: List<MultipartFile>,

        @RequestParam("countryId") countryId: UUID?,
        @RequestParam("newCountryName") newCountryName: String?,

        @RequestParam("placeName") placeName: String,
        @RequestParam("placeLatitude") placeLatitude: Double,
        @RequestParam("placeLongitude") placeLongitude: Double,
    ) {
        val userId = principal.getUserUUID()
        val storyCreateRequestDto = StoryCreateRequestDto(title, date, memo)

        val placeResponseDto = PlaceDto.PlaceIn().apply {
            this.name = placeName
            this.latitude = placeLatitude
            this.longtitude = placeLongitude
        }

        logger.info { "[createNewStory] userId=$userId storyCreateRequestDto=$storyCreateRequestDto placeDto=$placeResponseDto" }

        storyService.createNewStory(
            userId,
            multipartFiles,
            storyCreateRequestDto,
            countryId,
            newCountryName,
            placeResponseDto
        )
    }

    @PatchMapping("/{id}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    fun patchStory(
        @AuthenticationPrincipal principal: IfUserPrincipal,

        @PathVariable id: UUID,
        @RequestParam("title") title: String,

        @RequestParam("date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
        date: LocalDateTime,

        @RequestParam("memo") memo: String,
        @RequestParam("pictures") multipartFiles: List<MultipartFile>,

        @RequestParam("countryId") countryId: UUID?,
        @RequestParam("newCountryName") newCountryName: String?,

        @RequestParam("placeId") placeId: UUID?,
        @RequestParam("placeName") placeName: String?,
        @RequestParam("placeLatitude") placeLatitude: Double?,
        @RequestParam("placeLongitude") placeLongitude: Double?,
    ) {
        val userId = principal.getUserUUID()
        val storyPatchRequestDto = StoryPatchRequestDto(title, date, memo)

        val placeResponseDto = PlaceDto.PlaceIn().apply {
            this.name = placeName
            this.latitude = placeLatitude
            this.longtitude = placeLongitude
        }

        logger.info { "[patchStory] userId=$userId storyPatchRequestDto=$storyPatchRequestDto placeDto=$placeResponseDto" }

        storyService.patchStory(
            userId,
            id,
            multipartFiles,
            storyPatchRequestDto,
            countryId,
            newCountryName,
            placeId,
            placeResponseDto
        )
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    fun deleteStory(@PathVariable id: UUID) {
        storyService.deleteStory(id)
    }


    @GetMapping("/countries/{countryType}")
    fun getStoriesGroupByCountryType(
        @AuthenticationPrincipal principal: IfUserPrincipal,
        @PathVariable("countryType") countryType: String
    ): List<StoryResponseDto> {
        val userId = principal.getUserUUID()

        logger.info { "[getStoriesGroupByCountryType] userId=$userId countryType=$countryType" }

        return storyService.getStoriesByCountryType(userId, countryType.toUpperCase())
    }

}
