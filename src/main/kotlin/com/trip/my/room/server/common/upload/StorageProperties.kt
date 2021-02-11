package com.trip.my.room.server.common.upload

import java.util.*

interface StorageProperties {

    fun getBasePath(): String

    fun getBasePathWithStoryId(storyId: UUID?): String

}
