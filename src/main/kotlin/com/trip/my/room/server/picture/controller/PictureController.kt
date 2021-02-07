package com.trip.my.room.server.picture.controller

import com.trip.my.room.server.common.enum.PictureOrder
import com.trip.my.room.server.picture.PictureFile
import com.trip.my.room.server.picture.PictureUploadRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream

@RestController
@RequestMapping("/pictures")
class PictureController(private val pictureUploadRepository: PictureUploadRepository) {

    @PostMapping
    fun uploadPictureList(@RequestParam("pictures") multipartFiles: List<MultipartFile>) {
        val pictureFiles = IntStream.range(0, multipartFiles.size).boxed()
            .map { i -> PictureFile(multipartFiles.get(i), PictureOrder.fromInt(i + 1)) }
            .collect(Collectors.toList())
        pictureUploadRepository.getMappingPictureUrlWithOrder(UUID.randomUUID(), pictureFiles)
    }

}
