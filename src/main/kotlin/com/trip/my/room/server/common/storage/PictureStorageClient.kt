package com.trip.my.room.server.common.storage

import java.io.File
import java.net.URL

interface PictureStorageClient {

    fun uploadPicture(pictureFile: File, basePath: String): URL

    fun uploadPictureList(pictureFiles: List<File>, basePath: String): List<URL>

    fun deletePicture(filePath: String?)

    fun deleteBulkPictures(filePathList: List<String?>)

}
