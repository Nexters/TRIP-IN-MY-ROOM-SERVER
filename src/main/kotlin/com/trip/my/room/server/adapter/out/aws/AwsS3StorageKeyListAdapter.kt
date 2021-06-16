package com.trip.my.room.server.adapter.out.aws

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.DeleteObjectsRequest
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.trip.my.room.server.application.port.out.DeletePictureByStorageKeyListPort
import com.trip.my.room.server.application.port.out.FindPreSignedUriPort
import com.trip.my.room.server.config.AwsS3BucketProperties
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*
import kotlin.streams.toList

@Component
class AwsS3StorageKeyListAdapter(
    private val amazonS3: AmazonS3,
    private val awsS3BucketProperties: AwsS3BucketProperties
) : DeletePictureByStorageKeyListPort, FindPreSignedUriPort {

    override fun deletePictureByStorageKeyList(storageKeyCollection: Collection<String>) {
        val keyVersionList = storageKeyCollection.stream()
            .map { storageKey -> DeleteObjectsRequest.KeyVersion(storageKey) }
            .toList()

        amazonS3.deleteObjects(
            DeleteObjectsRequest(awsS3BucketProperties.bucketName)
                .withKeys(keyVersionList)
                .withQuiet(false)
        )
    }

    override fun findPreSignedUrl(storageKey: String): URL {
        val expiration = Date()
        var expTimeMillis = expiration.time
        expTimeMillis += (1000 * 60 * 10).toLong() // 10 min
        expiration.time = expTimeMillis

        val generatedPreSignedUrlRequest =
            GeneratePresignedUrlRequest(awsS3BucketProperties.bucketName, storageKey)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration)

        generatedPreSignedUrlRequest.putCustomRequestHeader("x-amz-acl", "public-read")

        return amazonS3.generatePresignedUrl(generatedPreSignedUrlRequest)
    }
}
