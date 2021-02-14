package com.trip.my.room.server.common.storage

import java.util.*

interface StorageProperties {

    fun getBasePath(): String

    fun getBasePathWithStoryId(storyId: UUID?): String

}
