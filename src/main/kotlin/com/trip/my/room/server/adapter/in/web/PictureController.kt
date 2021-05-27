package com.trip.my.room.server.adapter.`in`.web

import com.trip.my.room.server.application.port.`in`.CreatePreSignedUrlUseCase
import com.trip.my.room.server.application.port.`in`.DeletePictureUseCase
import com.trip.my.room.server.domain.picture.PreSignedUrlResponseDto
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pictures")
class PictureController(
    private val createPreSignedUrlUseCase: CreatePreSignedUrlUseCase,
    private val deletePictureUseCase: DeletePictureUseCase
) {

    companion object : KLogging()

    @PostMapping(value = ["/presigned"])
    fun getPreSignedUrls(@RequestParam("fileNameList") fileNameList: List<String>): List<PreSignedUrlResponseDto> {
        val preSignedUriList = createPreSignedUrlUseCase.createPreSignedUriList(fileNameList)

        logger.info { "preSignedUriList=${preSignedUriList}" }

        return preSignedUriList
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePictureList(@RequestParam("storageKeyList") storageKeyList: List<String>) {
        deletePictureUseCase.deletePicture(storageKeyList)
    }

}
