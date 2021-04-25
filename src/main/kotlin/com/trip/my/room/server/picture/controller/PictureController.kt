package com.trip.my.room.server.picture.controller

import com.trip.my.room.server.application.PreSignedUriService
import com.trip.my.room.server.common.enum.PictureOrder
import com.trip.my.room.server.picture.PictureFile
import com.trip.my.room.server.picture.PictureStorageRepository
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream

@RestController
@RequestMapping("/pictures")
class PictureController(
    private val pictureStorageRepository: PictureStorageRepository,
    private val preSignedUriService: PreSignedUriService
) {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadPictureList(
        @RequestParam("pictures") multipartFiles: List<MultipartFile>,
        @RequestParam("name") name: String,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
        @RequestParam("date") date: LocalDateTime
    ) {
        val pictureFiles = IntStream.range(0, multipartFiles.size).boxed()
            .map { i -> PictureFile(multipartFiles.get(i), PictureOrder.fromInt(i + 1)) }
            .collect(Collectors.toList())
        pictureStorageRepository.getMappingPictureUrlWithOrder(UUID.randomUUID(), pictureFiles)
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePicture(@RequestParam("filePath") filePath: String) {
        pictureStorageRepository.deletePicture(filePath)
    }

    @PostMapping(value = ["/presigned"])
    fun newUploadPicture(@RequestParam("fileNameList") fileNameList: List<String>): String {
        return preSignedUriService.getPreSignedUriList(fileNameList).toString()
    }

}
