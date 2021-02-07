package com.trip.my.room.server.picture

import com.trip.my.room.server.common.upload.PictureUploadClient
import com.trip.my.room.server.common.upload.UploadProperties
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.stream.Collectors

@Repository
class PictureUploadRepository(
    private val pictureUploadClient: PictureUploadClient,
    private val uploadProperties: UploadProperties
) {

    fun getMappingPictureUrlWithOrder(userId: UUID, pictureFiles: List<PictureFile>) {
        // TODO 사진을 꼭 order 순서대로 저장
        pictureFiles.sortedBy { it.order }

        val orderedPictureFiles = pictureFiles.stream()
            .map { pictures -> convertToFile(pictures.picture) }
            .collect(Collectors.toList())
        val uploadPictureList =
            pictureUploadClient.uploadPictureList(orderedPictureFiles, uploadProperties.getBasePathWithUserId(userId))

        orderedPictureFiles.forEach { file ->  removeFile(file) }
        // TODO 사진 url, order를 서로 매핑해서 매핑 정보 리턴
    }

    private fun convertToFile(multipartFile: MultipartFile): File {
        val convertFile = File(multipartFile.originalFilename)
        if (convertFile.createNewFile()) {
            val fos = FileOutputStream(convertFile)
            fos.write(multipartFile.bytes)
        }

        return convertFile
    }

    private fun removeFile(newFile: File) {
        newFile.delete()
    }
}
