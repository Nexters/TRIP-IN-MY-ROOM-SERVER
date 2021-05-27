package com.trip.my.room.server.application.port.out

import com.trip.my.room.server.domain.picture.PictureRequestDto
import com.trip.my.room.server.domain.picture.PictureResponseDto
import com.trip.my.room.server.domain.story.domain.model.StoryEntity

interface CreatePicturePort {

    fun createPicture(
        storyEntity: StoryEntity,
        pictureRequestDtoList: List<PictureRequestDto>
    ): List<PictureResponseDto>

}
