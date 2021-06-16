package com.trip.my.room.server.application.port.out

import com.trip.my.room.server.adapter.out.persistence.jpa.entity.StoryEntity
import com.trip.my.room.server.domain.picture.PictureRequestDto
import com.trip.my.room.server.domain.picture.PictureResponseDto

interface CreatePicturePort {

    fun createPicture(
        storyEntity: StoryEntity,
        pictureRequestDtoList: List<PictureRequestDto>
    ): List<PictureResponseDto>

}
