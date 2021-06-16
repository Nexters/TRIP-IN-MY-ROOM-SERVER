package com.trip.my.room.server.application.port.`in`

import com.trip.my.room.server.domain.picture.PictureRequestDto
import com.trip.my.room.server.domain.story.StoryCreateRequestDto
import com.trip.my.room.server.place.PlaceDto
import java.util.*

interface CreateStoryUseCase {

    fun createStory(createStoryCommand: CreateStoryCommand)

    data class CreateStoryCommand(
        val userId: UUID,
        val storyRequestDto: StoryCreateRequestDto,
        val pictureRequestDtoList: List<PictureRequestDto>,
        val countryId: UUID?,
        val newCountryName: String?,
        val placeInDto: PlaceDto.PlaceIn
    )

}
