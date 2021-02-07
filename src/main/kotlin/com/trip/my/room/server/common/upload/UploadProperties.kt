package com.trip.my.room.server.common.upload

import java.util.*

interface UploadProperties {

    fun getBasePath(): String

    fun getBasePathWithUserId(userId: UUID): String

}
