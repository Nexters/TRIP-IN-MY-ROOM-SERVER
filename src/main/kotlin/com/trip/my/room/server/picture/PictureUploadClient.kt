package com.trip.my.room.server.picture

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

interface PictureUploadClient {

    fun uploadPicture(pictureFile: MultipartFile): String

    fun uploadPictureList(pictureFiles: List<MultipartFile>): List<String>

}
