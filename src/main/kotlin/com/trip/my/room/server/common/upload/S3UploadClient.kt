package com.trip.my.room.server.common.upload

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import com.trip.my.room.server.config.AwsS3BucketProperties
import com.trip.my.room.server.config.AwsS3Properties
import org.springframework.stereotype.Component
import java.io.File
import java.util.stream.IntStream
import kotlin.streams.toList

@Component
class S3UploadClient(
    private val amazonS3: AmazonS3,
    private val awsS3BucketProperties: AwsS3BucketProperties
) : PictureUploadClient {

    override fun uploadPicture(pictureFile: File, basePath: String): String {
        val putObjectRequest = PutObjectRequest(
            awsS3BucketProperties.bucketName,
            createFileKey(pictureFile, basePath),
            pictureFile
        ).withCannedAcl(CannedAccessControlList.PublicRead)
        amazonS3.putObject(putObjectRequest)

        val url = amazonS3.getUrl(awsS3BucketProperties.bucketName, createFileKey(pictureFile, basePath))
        return url.toString()
    }

    override fun uploadPictureList(pictureFiles: List<File>, basePath: String): List<String> {
        return IntStream.range(0, pictureFiles.size).boxed()
            .map { uploadPicture(pictureFiles.get(it), basePath) }
            .toList()
    }

    fun createFileKey(pictureFile: File, basePath: String) = "${basePath}/${pictureFile.name}"

}
