package com.trip.my.room.server.common.storage

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.DeleteObjectsRequest
import com.amazonaws.services.s3.model.PutObjectRequest
import com.trip.my.room.server.config.AwsS3BucketProperties
import org.springframework.stereotype.Component
import java.io.File
import java.net.URL
import java.util.stream.IntStream
import kotlin.streams.toList

@Component
class S3Client(
    private val amazonS3: AmazonS3,
    private val awsS3BucketProperties: AwsS3BucketProperties
) : PictureStorageClient {

    override fun uploadPicture(pictureFile: File, basePath: String): URL {
        val putObjectRequest = PutObjectRequest(
            awsS3BucketProperties.bucketName,
            createFileKey(pictureFile, basePath),
            pictureFile
        ).withCannedAcl(CannedAccessControlList.PublicRead)
        amazonS3.putObject(putObjectRequest)

        return amazonS3.getUrl(awsS3BucketProperties.bucketName, createFileKey(pictureFile, basePath))
    }

    override fun uploadPictureList(pictureFiles: List<File>, basePath: String): List<URL> {
        return IntStream.range(0, pictureFiles.size).boxed()
            .map { uploadPicture(pictureFiles[it], basePath) }
            .toList()
    }

    fun createFileKey(pictureFile: File, basePath: String) = "${basePath}/${pictureFile.name}"

    override fun deletePicture(storageKey: String) {
        amazonS3.deleteObject(DeleteObjectRequest(awsS3BucketProperties.bucketName, storageKey))
    }

    override fun deleteBulkPictures(storageKeyList: List<String?>) {
        val keyVersionList = storageKeyList.stream()
            .map { storageKey -> DeleteObjectsRequest.KeyVersion(storageKey) }
            .toList()

        amazonS3.deleteObjects(
            DeleteObjectsRequest(awsS3BucketProperties.bucketName)
                .withKeys(keyVersionList)
                .withQuiet(false)
        )
    }
}
