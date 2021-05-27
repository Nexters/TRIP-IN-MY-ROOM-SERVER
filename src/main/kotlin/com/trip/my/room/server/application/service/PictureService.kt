package com.trip.my.room.server.application.service

import com.trip.my.room.server.application.port.`in`.CreatePreSignedUrlUseCase
import com.trip.my.room.server.application.port.`in`.DeletePictureUseCase
import com.trip.my.room.server.application.port.out.DeleteAllObjectInStoragePort
import com.trip.my.room.server.application.port.out.DeletePictureByStorageKey
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

    private val findPreSingedUriPort: FindPreSignedUriPort,
    private val deleteAllObjectInStoragePort: DeleteAllObjectInStoragePort,
    private val deletePictureByStorageKey: DeletePictureByStorageKey
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
        return PreSignedUrlResponseDto(fileName, storageKey, preSignedPictureUrl)
    }

    override fun deletePicture(storageKeyCollection: Collection<String>) {
        deleteAllObjectInStoragePort.deleteAllObjectInStorage(storageKeyCollection)
        storageKeyCollection.forEach { storageKey ->
            deletePictureByStorageKey.deletePictureByStorageKey(storageKey)
        }
    }

}
