package com.trip.my.room.server.application.service

import com.trip.my.room.server.application.port.`in`.CreatePreSignedUrlUseCase
import com.trip.my.room.server.application.port.`in`.DeletePictureUseCase
import com.trip.my.room.server.application.port.out.DeletePictureByStorageKeyListPort
import com.trip.my.room.server.application.port.out.DeletePictureByStorageKeyPort
import com.trip.my.room.server.application.port.out.FindPreSignedUriPort
import com.trip.my.room.server.domain.picture.PreSignedUrlResponseDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.streams.toList

@Service
class PictureService(
    @Value("\${amazon.s3.directoryPath:storyPictures}")
    private val directoryPath: String,

    @Value("\${amazon.s3.baseUrl:\"https://tripinmyroom.s3.ap-northeast-2.amazonaws.com/\"}")
    private val baseUrl: String,

    private val findPreSingedUriPort: FindPreSignedUriPort,
    private val deletePictureByStorageKeyListPort: DeletePictureByStorageKeyListPort,
    private val deletePictureByStorageKeyPort: DeletePictureByStorageKeyPort
) : CreatePreSignedUrlUseCase, DeletePictureUseCase {

    override fun createPreSignedUriList(fileNameList: List<String>): List<PreSignedUrlResponseDto> {
        return fileNameList.stream()
            .map { fileName -> convertToPreSignedUrlResponseDto(fileName) }
            .toList()
    }

    private fun appendingDirectoryPathAndTime(preFileName: String): String {
        return "${directoryPath}/${Instant.now()}-${preFileName}"
    }

    private fun convertToPreSignedUrlResponseDto(fileName: String): PreSignedUrlResponseDto {
        val storageKey = appendingDirectoryPathAndTime(fileName)
        val preSignedPictureUrl = findPreSingedUriPort.findPreSignedUrl(storageKey)
        return PreSignedUrlResponseDto(fileName, storageKey, getPictureUrl(storageKey), preSignedPictureUrl)
    }

    private fun getPictureUrl(storageKey: String): String {
        return "${baseUrl}/${storageKey}"
    }

    override fun deletePicture(storageKeyCollection: Collection<String>) {
        deletePictureByStorageKeyListPort.deletePictureByStorageKeyList(storageKeyCollection)
        storageKeyCollection.forEach { storageKey ->
            deletePictureByStorageKeyPort.deletePictureByStorageKey(storageKey)
        }
    }

}
