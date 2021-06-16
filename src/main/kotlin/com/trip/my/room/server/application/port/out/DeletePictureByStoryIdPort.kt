package com.trip.my.room.server.application.port.out

import java.util.*

interface DeletePictureByStoryIdPort {

    fun deletePictureByStoryId(storyId: UUID)

}
