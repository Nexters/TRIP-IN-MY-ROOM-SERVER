package com.trip.my.room.server.common.upload

import java.io.File

interface PictureUploadClient {

    fun uploadPicture(pictureFile: File, basePath: String): String

    fun uploadPictureList(pictureFiles: List<File>, basePath: String): List<String>

}
