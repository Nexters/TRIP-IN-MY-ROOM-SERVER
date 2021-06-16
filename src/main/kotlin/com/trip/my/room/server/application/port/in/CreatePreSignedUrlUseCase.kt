package com.trip.my.room.server.application.port.`in`

import com.trip.my.room.server.domain.picture.PreSignedUrlResponseDto

interface CreatePreSignedUrlUseCase {

    fun createPreSignedUriList(fileNameList: List<String>): List<PreSignedUrlResponseDto>

}
