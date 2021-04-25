package com.trip.my.room.server.adapter.infrastructure

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.trip.my.room.server.port.PreSignedPictureUri
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*


@Component
class PreSignedPictureUriFromAws(
    @Value("\${cloud.aws.s3.bucket:tripinmyroom}")
    private val bucketName: String,

    private val amazonS3: AmazonS3,
) : PreSignedPictureUri {

    override fun getPreSignedPictureUrls(fileNameList: List<String>): List<URL> {
        val expiration = Date()
        var expTimeMillis = expiration.time
        expTimeMillis += (1000 * 60 * 10).toLong() // 10 min
        expiration.time = expTimeMillis

        val preSignedPictureUrlList = mutableListOf<URL>()
        for (fileName in fileNameList) {
            val generatePreSignedUrlRequest = GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration)
            val url = amazonS3.generatePresignedUrl(generatePreSignedUrlRequest)
            preSignedPictureUrlList.add(url)
        }

        return preSignedPictureUrlList.toList()
    }

}
