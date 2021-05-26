package com.trip.my.room.server.application

import com.trip.my.room.server.domain.picture.PreSignedUrlResponseDto
import com.trip.my.room.server.port.PreSignedPictureUriPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.streams.toList

@Service
class PreSignedUriService(
    @Value("\${amazon.s3.directoryPath:storyPictures}")
    private val directoryPath: String,

    private val preSingedPictureUriPort: PreSignedPictureUriPort
) {

    fun getPreSignedUriList(fileNameList: List<String>): List<PreSignedUrlResponseDto> {
        return fileNameList.stream()
            .map { fileName -> convertToPreSignedUrlResponseDto(fileName) }
            .toList()
    }

    private fun appendingDirectoryPathAndTime(preFileName: String): String {
        return "${directoryPath}/${Instant.now()}-${preFileName}"
    }

    private fun convertToPreSignedUrlResponseDto(fileName: String): PreSignedUrlResponseDto {
        val storageKey = appendingDirectoryPathAndTime(fileName)
        val preSignedPictureUrl = preSingedPictureUriPort.getPreSignedPictureUrl(storageKey)
        return PreSignedUrlResponseDto(fileName, storageKey, preSignedPictureUrl)
    }

}
