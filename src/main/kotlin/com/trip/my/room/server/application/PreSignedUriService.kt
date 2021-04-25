package com.trip.my.room.server.application

import com.trip.my.room.server.port.PreSignedPictureUri
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URL
import java.time.Instant
import kotlin.streams.toList

@Service
class PreSignedUriService(
    @Value("\${amazon.s3.directoryPath:storyPictures}")
    private val directoryPath: String,

    private val preSingedPictureUri: PreSignedPictureUri
) {

    fun getPreSignedUriList(fileNameList: List<String>): List<URL> {
        val fileNameListWithDirectories = fileNameList.stream()
            .map { fileName -> appendingDirectories(fileName) }
            .toList()

        return preSingedPictureUri.getPreSignedPictureUrls(fileNameListWithDirectories)
    }

    private fun appendingDirectories(preFileName: String): String {
        return "${directoryPath}/${Instant.now()}-${preFileName}"
    }

}
