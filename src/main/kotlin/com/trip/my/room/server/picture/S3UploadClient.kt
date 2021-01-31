package com.trip.my.room.server.picture

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class S3UploadClient : PictureUploadClient {

    override fun uploadPictureList(pictureFiles: List<MultipartFile>): List<String> {
        TODO("Not yet implemented")

        return emptyList()
    }

    override fun uploadPicture(picture: MultipartFile): String {
        TODO("Return picture url")
        return "hello world"
    }

}
