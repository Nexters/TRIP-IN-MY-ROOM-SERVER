package com.trip.my.room.server.adapter.`in`.web

import com.trip.my.room.server.application.PreSignedUriService
import com.trip.my.room.server.domain.picture.PictureStorageRepository
import com.trip.my.room.server.domain.picture.PreSignedUrlResponseDto
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pictures")
class PictureController(
    private val pictureStorageRepository: PictureStorageRepository,
    private val preSignedUriService: PreSignedUriService
) {

    companion object : KLogging()

    @PostMapping(value = ["/presigned"])
    fun getPreSignedUrls(@RequestParam("fileNameList") fileNameList: List<String>): List<PreSignedUrlResponseDto> {
        val preSignedUriList = preSignedUriService.getPreSignedUriList(fileNameList)

        logger.info { "preSignedUriList=${preSignedUriList}" }

        return preSignedUriList
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePictureList(@RequestParam("storageKeyList") storageKeyList: List<String>) {
        pictureStorageRepository.deleteBulkPictures(storageKeyList)
    }

}
