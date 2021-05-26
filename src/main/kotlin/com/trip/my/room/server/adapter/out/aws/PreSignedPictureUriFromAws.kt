package com.trip.my.room.server.adapter.out.aws

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.trip.my.room.server.port.PreSignedPictureUriPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*


@Component
class PreSignedPictureUriFromAws(
    @Value("\${cloud.aws.s3.bucket:tripinmyroom}")
    private val bucketName: String,

    private val amazonS3: AmazonS3,
) : PreSignedPictureUriPort {

    override fun getPreSignedPictureUrl(storageKey: String): URL {
        val expiration = Date()
        var expTimeMillis = expiration.time
        expTimeMillis += (1000 * 60 * 10).toLong() // 10 min
        expiration.time = expTimeMillis

        val generatedPreSignedUrlRequest = GeneratePresignedUrlRequest(bucketName, storageKey)
            .withMethod(HttpMethod.PUT)
            .withExpiration(expiration)

        generatedPreSignedUrlRequest.putCustomRequestHeader("x-amz-acl", "public-read")

        return amazonS3.generatePresignedUrl(generatedPreSignedUrlRequest)
    }

}
