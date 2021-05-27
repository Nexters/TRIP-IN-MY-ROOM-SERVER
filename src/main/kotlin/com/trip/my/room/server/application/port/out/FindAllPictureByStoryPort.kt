package com.trip.my.room.server.application.port.out

import com.trip.my.room.server.domain.picture.PictureResponseDto
import java.util.*

interface FindAllPictureByStoryPort {

    fun findAllPictureByStoryId(storyId: UUID?): List<PictureResponseDto>

}