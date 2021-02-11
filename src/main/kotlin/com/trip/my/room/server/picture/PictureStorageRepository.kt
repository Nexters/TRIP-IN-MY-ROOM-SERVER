package com.trip.my.room.server.picture

import com.trip.my.room.server.common.enum.PictureOrder
import com.trip.my.room.server.common.storage.PictureStorageClient
import com.trip.my.room.server.common.storage.StorageProperties
import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.time.LocalDate
import java.util.*
import java.util.stream.IntStream
import kotlin.streams.toList

@Repository
class PictureStorageRepository(
    private val pictureStorageClient: PictureStorageClient,
    private val storageProperties: StorageProperties
) {

    fun getMappingPictureUrlWithOrder(
        storyId: UUID?,
        pictureFiles: List<PictureFile>
    ): List<PictureUploadedUrl> {
        pictureFiles.sortedBy { it.order }

        var orderIndex = 1
        val orderedPictureFiles = pictureFiles.stream()
            .map { pictures -> convertToFile(pictures.picture, orderIndex++) }
            .toList()

        val uploadPictureUrlList =
            pictureStorageClient.uploadPictureList(
                orderedPictureFiles,
                storageProperties.getBasePathWithStoryId(storyId)
            )

        orderedPictureFiles.forEach { file -> removeFile(file) }

        return makeUploadedPictureUrlWithOrder(uploadPictureUrlList)
    }

    private fun makeUploadedPictureUrlWithOrder(uploadPictureUrlList: List<URL>): List<PictureUploadedUrl> {
        return IntStream.range(0, uploadPictureUrlList.size).boxed()
            .map { i -> PictureUploadedUrl(uploadPictureUrlList[i], PictureOrder.fromInt(i + 1)) }
            .toList()
    }

    private fun convertToFile(multipartFile: MultipartFile, index: Int): File {
        val originalFileExtension = FilenameUtils.getExtension(multipartFile.originalFilename)
        val convertFile = File("${LocalDate.now()}-0$index.$originalFileExtension")
        if (convertFile.createNewFile()) {
            val fos = FileOutputStream(convertFile)
            fos.write(multipartFile.bytes)
        }

        return convertFile
    }

    private fun removeFile(newFile: File) {
        newFile.delete()
    }

    fun deletePicture(filePath: String?) {
        pictureStorageClient.deletePicture(filePath)
    }

    fun deleteBulkPictures(filePathList: List<String?>) {
        pictureStorageClient.deleteBulkPictures(filePathList)
    }
}
